package lowsw;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lowsw.domain.Action;
import lowsw.domain.CampaignState;
import lowsw.domain.ClassType;
import lowsw.domain.Hero;
import lowsw.domain.Party;
import lowsw.domain.User;
import lowsw.persistence.InMemoryCampaignRepository;
import lowsw.persistence.InMemoryPvPRepository;
import lowsw.persistence.InMemoryUserRepository;
import lowsw.service.ActionType;
import lowsw.service.AuthService;
import lowsw.service.BasicDamageStrategy;
import lowsw.service.BattleEngine;
import lowsw.service.BattleState;
import lowsw.service.CampaignService;
import lowsw.service.DefaultHeroFactory;
import lowsw.service.InnService;
import lowsw.service.PvPService;

public class Deliverable2Test {
    private AuthService authService;
    private CampaignService campaignService;
    private InnService innService;
    private PvPService pvpService;
    private DefaultHeroFactory heroFactory;

    @BeforeEach
    void setup() {
        authService = new AuthService(new InMemoryUserRepository());
        heroFactory = new DefaultHeroFactory();
        campaignService = new CampaignService(new InMemoryCampaignRepository(), heroFactory);
        innService = new InnService();
        pvpService = new PvPService(new InMemoryPvPRepository());
    }

    @Test
    void registerCreatesUser() {
        assertEquals("alex", authService.register("alex", "pw").getUsername());
    }

    @Test
    void loginFailsForWrongPassword() {
        authService.register("alex", "pw");
        assertNull(authService.login("alex", "wrong"));
    }

    @Test
    void startCampaignCreatesStarterHero() {
        CampaignState state = campaignService.startNew(new User(1, "alex", "HASH::pw"), ClassType.ORDER);
        assertEquals(1, state.getParty().getHeroes().size());
        assertEquals(1, state.getRoomNumber());
    }

    @Test
    void attackReducesEnemyHp() {
        Party a = new Party(0);
        a.addHero(heroFactory.createHero(ClassType.ORDER, "A", 1));

        Party b = new Party(0);
        b.addHero(heroFactory.createHero(ClassType.CHAOS, "B", 1));

        BattleEngine engine = new BattleEngine(new BasicDamageStrategy());
        BattleState state = engine.initBattle(a, b);

        int before = b.getHero(0).getHp();
        engine.applyAction(state, new Action(0, 0, ActionType.ATTACK));

        assertTrue(b.getHero(0).getHp() < before);
    }

    @Test
    void defendIncreasesHpAndMana() {
        Party a = new Party(0);
        a.addHero(heroFactory.createHero(ClassType.ORDER, "A", 1));

        Party b = new Party(0);
        b.addHero(heroFactory.createHero(ClassType.CHAOS, "B", 1));

        BattleEngine engine = new BattleEngine(new BasicDamageStrategy());
        BattleState state = engine.initBattle(a, b);

        int hp = a.getHero(0).getHp();
        int mana = a.getHero(0).getMana();

        engine.applyAction(state, new Action(0, 0, ActionType.DEFEND));

        assertTrue(a.getHero(0).getHp() > hp);
        assertTrue(a.getHero(0).getMana() > mana);
    }

    @Test
    void castRequiresEnoughMana() {
        Party a = new Party(0);
        a.addHero(new Hero("Mage", ClassType.MAGE, 1, 40, 0, 20, 1));

        Party b = new Party(0);
        b.addHero(heroFactory.createHero(ClassType.CHAOS, "B", 1));

        BattleEngine engine = new BattleEngine(new BasicDamageStrategy());
        BattleState state = engine.initBattle(a, b);

        assertThrows(IllegalStateException.class,
                () -> engine.applyAction(state, new Action(0, 0, ActionType.CAST)));
    }

    @Test
    void buyItemCostsGoldAndAddsToInventory() {
        CampaignState state = campaignService.startNew(new User(2, "dave", "HASH::pw"), ClassType.MAGE);
        int gold = state.getParty().getGold();

        boolean bought = innService.buyItem(state, "Potion", 25);

        assertTrue(bought);
        assertEquals(gold - 25, state.getParty().getGold());
        assertTrue(state.getInventory().contains("Potion"));
    }

    @Test
    void recruitHeroAddsHeroToParty() {
        CampaignState state = campaignService.startNew(new User(2, "dave", "HASH::pw"), ClassType.MAGE);

        boolean recruited = innService.recruitHero(state, ClassType.ORDER, "Knight", 100);

        assertTrue(recruited);
        assertEquals(2, state.getParty().getHeroes().size());
    }

    @Test
    void exitCampaignMarksNotInProgress() {
        CampaignState state = campaignService.startNew(new User(2, "dave", "HASH::pw"), ClassType.MAGE);
        campaignService.exit(state);
        assertFalse(state.isInProgress());
    }

    @Test
    void continueCampaignLoadsSavedState() {
        InMemoryCampaignRepository repo = new InMemoryCampaignRepository();
        CampaignService service = new CampaignService(repo, heroFactory);
        CampaignState state = service.startNew(new User(3, "kim", "HASH::pw"), ClassType.WARRIOR);

        assertNotNull(service.continueCampaign(state.getUserId()));
    }

    @Test
    void sendInviteCreatesPendingInvitation() {
        var invite = pvpService.sendInvitation("jameson", "dave");
        assertEquals("PENDING", invite.getStatus());
    }

    @Test
    void acceptInviteChangesStatus() {
        var invite = pvpService.sendInvitation("jameson", "dave");
        var accepted = pvpService.acceptInvitation(invite.getId());
        assertEquals("ACCEPTED", accepted.getStatus());
    }
}
