package lowsw;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import lowsw.controller.BattleController;
import lowsw.domain.Action;
import lowsw.domain.CampaignState;
import lowsw.domain.ClassType;
import lowsw.domain.Hero;
import lowsw.domain.Party;
import lowsw.domain.PvPInvitation;
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

public class DemoLauncher extends JFrame {

    private final JTextArea outputArea;

    private final AuthService authService;
    private final CampaignService campaignService;
    private final PvPService pvpService;
    private final DefaultHeroFactory heroFactory;
    private final InnService innService;

    private final List<User> leagueUsers;

    private User currentUser;
    private User demoOpponent;
    private BattleController battleController;
    private BattleState currentBattle;
    private CampaignState currentCampaign;
    private PvPInvitation lastInvite;

    public DemoLauncher() {
        setTitle("Legends of Sword and Wand Demo");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        authService = new AuthService(new InMemoryUserRepository());
        heroFactory = new DefaultHeroFactory();
        campaignService = new CampaignService(new InMemoryCampaignRepository(), heroFactory);
        pvpService = new PvPService(new InMemoryPvPRepository());
        innService = new InnService();

        leagueUsers = new ArrayList<>();

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel topPanel = new JPanel(new GridLayout(4, 1, 8, 8));
        JPanel authPanel = new JPanel(new GridLayout(1, 5, 8, 8));
        JPanel campaignPanel = new JPanel(new GridLayout(1, 8, 8, 8));
        JPanel innPanel = new JPanel(new GridLayout(1, 5, 8, 8));
        JPanel battlePvPPanel = new JPanel(new GridLayout(2, 6, 8, 8));

        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");
        JButton showUserButton = new JButton("Show Current User");
        JButton showStandingsButton = new JButton("Show Standings");
        JButton clearButton = new JButton("Clear Output");

        JButton startCampaignButton = new JButton("Start Campaign");
        JButton showPartyButton = new JButton("Show Party");
        JButton showInventoryButton = new JButton("Show Inventory");
        JButton nextRoomButton = new JButton("Next Room");
        JButton exitCampaignButton = new JButton("Exit Campaign");
        JButton continueCampaignButton = new JButton("Continue Campaign");
        JButton savePartyButton = new JButton("Save Party");
        JButton finishCampaignButton = new JButton("Finish Campaign");

        JButton restButton = new JButton("Rest at Inn");
        JButton buyItemButton = new JButton("Buy Item");
        JButton useItemButton = new JButton("Use Item");
        JButton recruitHeroButton = new JButton("Recruit Hero");
        JButton showInnStatusButton = new JButton("Show Inn Status");

        JButton startBattleButton = new JButton("Start Battle");
        JButton attackButton = new JButton("Attack");
        JButton defendButton = new JButton("Defend");
        JButton waitButton = new JButton("Wait");
        JButton castButton = new JButton("Cast");
        JButton sendInviteButton = new JButton("Send PvP Invite");
        JButton acceptInviteButton = new JButton("Accept PvP Invite");
        JButton startPvPBattleButton = new JButton("Start PvP Battle");

        registerButton.addActionListener(e -> registerUser());
        loginButton.addActionListener(e -> loginUser());
        showUserButton.addActionListener(e -> showCurrentUser());
        showStandingsButton.addActionListener(e -> showStandings());
        clearButton.addActionListener(e -> outputArea.setText(""));

        startCampaignButton.addActionListener(e -> startCampaign());
        showPartyButton.addActionListener(e -> showParty());
        showInventoryButton.addActionListener(e -> showInventory());
        nextRoomButton.addActionListener(e -> nextRoom());
        exitCampaignButton.addActionListener(e -> exitCampaign());
        continueCampaignButton.addActionListener(e -> continueCampaign());
        savePartyButton.addActionListener(e -> saveCurrentParty());
        finishCampaignButton.addActionListener(e -> finishCampaign());

        restButton.addActionListener(e -> restAtInn());
        buyItemButton.addActionListener(e -> buyItem());
        useItemButton.addActionListener(e -> useItem());
        recruitHeroButton.addActionListener(e -> recruitHero());
        showInnStatusButton.addActionListener(e -> showInnStatus());

        startBattleButton.addActionListener(e -> startBattle());
        attackButton.addActionListener(e -> doBattleAction(ActionType.ATTACK));
        defendButton.addActionListener(e -> doBattleAction(ActionType.DEFEND));
        waitButton.addActionListener(e -> doBattleAction(ActionType.WAIT));
        castButton.addActionListener(e -> doBattleAction(ActionType.CAST));

        sendInviteButton.addActionListener(e -> sendPvPInvite());
        acceptInviteButton.addActionListener(e -> acceptPvPInvite());
        startPvPBattleButton.addActionListener(e -> startPvPBattle());

        authPanel.add(registerButton);
        authPanel.add(loginButton);
        authPanel.add(showUserButton);
        authPanel.add(showStandingsButton);
        authPanel.add(clearButton);

        campaignPanel.add(startCampaignButton);
        campaignPanel.add(showPartyButton);
        campaignPanel.add(showInventoryButton);
        campaignPanel.add(nextRoomButton);
        campaignPanel.add(exitCampaignButton);
        campaignPanel.add(continueCampaignButton);
        campaignPanel.add(savePartyButton);
        campaignPanel.add(finishCampaignButton);

        innPanel.add(restButton);
        innPanel.add(buyItemButton);
        innPanel.add(useItemButton);
        innPanel.add(recruitHeroButton);
        innPanel.add(showInnStatusButton);

        battlePvPPanel.add(startBattleButton);
        battlePvPPanel.add(attackButton);
        battlePvPPanel.add(defendButton);
        battlePvPPanel.add(waitButton);
        battlePvPPanel.add(castButton);
        battlePvPPanel.add(sendInviteButton);
        battlePvPPanel.add(acceptInviteButton);
        battlePvPPanel.add(startPvPBattleButton);

        topPanel.add(authPanel);
        topPanel.add(campaignPanel);
        topPanel.add(innPanel);
        topPanel.add(battlePvPPanel);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        append("=== Legends of Sword and Wand Demo ===");
        append("Use the buttons above to interact with the system.");

        setupDemoOpponent();
    }

    private void setupDemoOpponent() {
        demoOpponent = new User(2, "dave", "HASH::demo");
        Party savedParty = new Party(200);
        savedParty.addHero(heroFactory.createHero(ClassType.CHAOS, "DaveGoblin", 1));
        demoOpponent.getSavedParties().add(savedParty);
        leagueUsers.add(demoOpponent);
    }

    private void append(String text) {
        outputArea.append(text + "\n");
    }

    private void registerUser() {
        String username = JOptionPane.showInputDialog(this, "Enter username to register:");
        if (username == null || username.isBlank()) {
            append("Registration cancelled.");
            return;
        }

        String password = JOptionPane.showInputDialog(this, "Enter password:");
        if (password == null || password.isBlank()) {
            append("Registration cancelled.");
            return;
        }

        try {
            User user = authService.register(username, password);
            append("Registered user: " + user.getUsername());
            if (!leagueUsers.contains(user)) {
                leagueUsers.add(user);
            }
        } catch (Exception ex) {
            append("Registration failed: username already exists.");
        }
    }

    private void loginUser() {
        String username = JOptionPane.showInputDialog(this, "Enter username to login:");
        if (username == null || username.isBlank()) {
            append("Login cancelled.");
            return;
        }

        String password = JOptionPane.showInputDialog(this, "Enter password:");
        if (password == null || password.isBlank()) {
            append("Login cancelled.");
            return;
        }

        User user = authService.login(username, password);
        if (user != null) {
            currentUser = user;
            if (!leagueUsers.contains(currentUser)) {
                leagueUsers.add(currentUser);
            }
            append("Login successful for user: " + currentUser.getUsername());
            append("Dashboard ready. You may now start PvE or PvP.");
            append("Saved parties: " + currentUser.getSavedParties().size());
            append("Best score: " + currentUser.getBestScore());
            append("PvP record: " + currentUser.getWins() + "W - " + currentUser.getLosses() + "L");
        } else {
            append("Login failed: incorrect username or password.");
        }
    }

    private void showCurrentUser() {
        if (currentUser == null) {
            append("No user is currently logged in.");
        } else {
            append("Current user: " + currentUser.getUsername());
            append("Saved parties: " + currentUser.getSavedParties().size());
            append("Best score: " + currentUser.getBestScore());
            append("PvP record: " + currentUser.getWins() + "W - " + currentUser.getLosses() + "L");
        }
    }

    private void startCampaign() {
        if (currentUser == null) {
            append("Please log in first before starting a campaign.");
            return;
        }

        String[] options = {"ORDER", "CHAOS", "WARRIOR", "MAGE"};
        String selected = (String) JOptionPane.showInputDialog(
                this,
                "Choose a starter class:",
                "Start Campaign",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (selected == null) {
            append("Campaign start cancelled.");
            return;
        }

        ClassType classType = ClassType.valueOf(selected);
        currentCampaign = campaignService.startNew(currentUser, classType);

        append("=== Campaign Started ===");
        append("User: " + currentUser.getUsername());
        append("Starter class: " + classType);
        append("Current room: " + currentCampaign.getRoomNumber());
        append("Last room type: " + currentCampaign.getLastRoomType());
    }

    private void saveCurrentParty() {
        if (currentUser == null || currentCampaign == null) {
            append("Start a campaign first.");
            return;
        }

        if (currentUser.getSavedParties().contains(currentCampaign.getParty())) {
            append("Current party is already saved.");
            return;
        }

        if (currentUser.getSavedParties().size() >= 5) {
            String replaceIndex = JOptionPane.showInputDialog(this,
                    "You already have 5 saved parties.\nEnter party index (1-5) to replace:");
            if (replaceIndex == null || replaceIndex.isBlank()) {
                append("Save party cancelled.");
                return;
            }

            try {
                int idx = Integer.parseInt(replaceIndex) - 1;
                if (idx < 0 || idx >= currentUser.getSavedParties().size()) {
                    append("Invalid party index.");
                    return;
                }
                currentUser.getSavedParties().set(idx, currentCampaign.getParty());
                append("Saved party replaced successfully.");
            } catch (NumberFormatException ex) {
                append("Invalid party index.");
            }
        } else {
            currentUser.getSavedParties().add(currentCampaign.getParty());
            append("Current party saved successfully.");
        }

        append("Saved parties now: " + currentUser.getSavedParties().size());
    }

    private void showParty() {
        if (currentCampaign == null) {
            append("No active campaign.");
            return;
        }

        append("=== Party Status ===");
        append("Gold: " + currentCampaign.getParty().getGold());
        for (Hero hero : currentCampaign.getParty().getHeroes()) {
            append(hero.toString());
        }
    }

    private void showInventory() {
        if (currentCampaign == null) {
            append("No active campaign.");
            return;
        }

        append("=== Inventory ===");
        if (currentCampaign.getInventory().isEmpty()) {
            append("Inventory is empty.");
        } else {
            for (String item : currentCampaign.getInventory()) {
                append("- " + item);
            }
        }
    }

    private void nextRoom() {
        if (currentCampaign == null) {
            append("Start a campaign first.");
            return;
        }

        String[] roomOptions = {"BATTLE", "INN"};
        String selected = (String) JOptionPane.showInputDialog(
                this,
                "Choose next room type:",
                "Next Room",
                JOptionPane.PLAIN_MESSAGE,
                null,
                roomOptions,
                roomOptions[0]
        );

        if (selected == null) {
            append("Next room selection cancelled.");
            return;
        }

        campaignService.nextRoom(currentCampaign, selected);
        append("Moved to next room.");
        append("Current room: " + currentCampaign.getRoomNumber());
        append("Last room type: " + currentCampaign.getLastRoomType());

        if ("INN".equals(selected)) {
            append("Inn view restored. Heroes can rest and prepare.");
            currentCampaign.getInventory().add("Potion");
            append("Sample item added to inventory: Potion");
        } else {
            append("Battle room entered.");
        }
    }

    private void finishCampaign() {
        if (currentCampaign == null) {
            append("No active campaign.");
            return;
        }

        currentCampaign.setRoomNumber(30);
        int score = currentCampaign.getParty().getGold() + currentCampaign.getParty().getHeroes().size() * 100;
        currentCampaign.setScore(score);
        currentUser.updateBestScore(score);

        append("=== Campaign Completed ===");
        append("Final room reached: 30");
        append("Final score: " + currentCampaign.getScore());
        append("Profile best score updated to: " + currentUser.getBestScore());
    }

    private void restAtInn() {
        if (currentCampaign == null || !"INN".equals(currentCampaign.getLastRoomType())) {
            append("You must be at an inn to rest.");
            return;
        }

        innService.rest(currentCampaign);
        append("All heroes were healed and mana was restored.");
        showParty();
    }

    private void buyItem() {
        if (currentCampaign == null || !"INN".equals(currentCampaign.getLastRoomType())) {
            append("You must be at an inn to buy items.");
            return;
        }

        String[] items = {"Potion", "Elixir", "Revive Stone"};
        String selected = (String) JOptionPane.showInputDialog(
                this,
                "Choose item to buy:",
                "Buy Item",
                JOptionPane.PLAIN_MESSAGE,
                null,
                items,
                items[0]
        );

        if (selected == null) {
            append("Buy item cancelled.");
            return;
        }

        int cost = switch (selected) {
            case "Potion" -> 25;
            case "Elixir" -> 35;
            case "Revive Stone" -> 50;
            default -> 25;
        };

        boolean ok = innService.buyItem(currentCampaign, selected, cost);
        if (ok) {
            append("Bought item: " + selected);
            append("Gold remaining: " + currentCampaign.getParty().getGold());
        } else {
            append("Not enough gold to buy: " + selected);
        }
    }

    private void useItem() {
        if (currentCampaign == null) {
            append("No active campaign.");
            return;
        }

        if (currentCampaign.getInventory().isEmpty()) {
            append("No items available to use.");
            return;
        }

        String selected = (String) JOptionPane.showInputDialog(
                this,
                "Choose item to use:",
                "Use Item",
                JOptionPane.PLAIN_MESSAGE,
                null,
                currentCampaign.getInventory().toArray(),
                currentCampaign.getInventory().get(0)
        );

        if (selected == null) {
            append("Use item cancelled.");
            return;
        }

        boolean ok = innService.useItem(currentCampaign, selected, 0);
        if (ok) {
            append("Used item: " + selected + " on first hero.");
            showParty();
        } else {
            append("Could not use item: " + selected);
        }
    }

    private void recruitHero() {
        if (currentCampaign == null || !"INN".equals(currentCampaign.getLastRoomType())) {
            append("You must be at an inn to recruit heroes.");
            return;
        }

        String name = JOptionPane.showInputDialog(this, "Enter hero name:");
        if (name == null || name.isBlank()) {
            append("Recruit cancelled.");
            return;
        }

        String[] options = {"ORDER", "CHAOS", "WARRIOR", "MAGE"};
        String selected = (String) JOptionPane.showInputDialog(
                this,
                "Choose hero class:",
                "Recruit Hero",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (selected == null) {
            append("Recruit cancelled.");
            return;
        }

        boolean ok = innService.recruitHero(currentCampaign, ClassType.valueOf(selected), name, 100);
        if (ok) {
            append("Hero recruited successfully: " + name);
            showParty();
        } else {
            append("Could not recruit hero. Check party size or gold.");
        }
    }

    private void showInnStatus() {
        if (currentCampaign == null || !"INN".equals(currentCampaign.getLastRoomType())) {
            append("You are not currently at an inn.");
            return;
        }

        append("=== Inn Status ===");
        append("Party members can be healed, items can be bought, and heroes can be recruited.");
        showParty();
        showInventory();
    }

    private void startBattle() {
        Party player = new Party(500);
        player.addHero(new Hero("Alice", ClassType.ORDER, 1, 50, 20, 12, 4));

        Party enemy = new Party(0);
        enemy.addHero(new Hero("Goblin", ClassType.CHAOS, 1, 40, 10, 8, 2));

        battleController = new BattleController(new BattleEngine(new BasicDamageStrategy()));
        currentBattle = battleController.start(player, enemy);

        append("=== Battle Started ===");
        append("Player hero: " + player.getHeroes().get(0));
        append("Enemy hero: " + enemy.getHeroes().get(0));
        append("Choose Attack, Defend, Wait, or Cast.");
    }

    private void doBattleAction(ActionType actionType) {
        if (currentBattle == null) {
            append("Start a battle first.");
            return;
        }

        if (currentBattle.isFinished()) {
            append("Battle already finished.");
            return;
        }

        Party player = currentBattle.getPlayerParty();
        Party enemy = currentBattle.getEnemyParty();

        int enemyHpBefore = enemy.getHeroes().get(0).getHp();
        int playerHpBefore = player.getHeroes().get(0).getHp();
        int playerManaBefore = player.getHeroes().get(0).getMana();

        try {
            battleController.act(currentBattle, new Action(0, 0, actionType));
            append("Action chosen: " + actionType);
            append("Enemy HP: " + enemyHpBefore + " -> " + enemy.getHeroes().get(0).getHp());
            append("Player HP: " + playerHpBefore + " -> " + player.getHeroes().get(0).getHp());
            append("Player Mana: " + playerManaBefore + " -> " + player.getHeroes().get(0).getMana());

            if (currentBattle.getResult() == null) {
                append("Battle result: still in progress");
            } else {
                append("=== Battle Finished ===");
                append("Battle result: " + currentBattle.getResult());
                append("Surviving player heroes:");
                for (Hero hero : currentBattle.getPlayerParty().getHeroes()) {
                    if (hero.getHp() > 0) {
                        append(hero.toString());
                    }
                }
            }
        } catch (Exception ex) {
            append("Battle action failed: " + ex.getMessage());
        }
    }

    private void exitCampaign() {
        if (currentBattle != null && !currentBattle.isFinished()) {
            append("Cannot exit campaign during an active battle.");
            return;
        }

        if (currentCampaign == null) {
            append("No active campaign to exit.");
            return;
        }

        campaignService.exit(currentCampaign);
        append("Campaign exited and saved.");
        append("In progress: " + currentCampaign.isInProgress());
    }

    private void continueCampaign() {
        if (currentUser == null) {
            append("Please log in first.");
            return;
        }

        var loaded = campaignService.continueCampaign(currentUser.getId());
        if (loaded == null) {
            append("No saved campaign found.");
            return;
        }

        currentCampaign = loaded;
        append("Campaign loaded successfully.");
        append("Current room: " + currentCampaign.getRoomNumber());
        append("Last room type: " + currentCampaign.getLastRoomType());
        append("In progress: " + currentCampaign.isInProgress());

        if ("INN".equals(currentCampaign.getLastRoomType())) {
            append("Restored inn view.");
        } else {
            append("Restored campaign view.");
        }
    }

    private void sendPvPInvite() {
        if (currentUser == null) {
            append("Please log in first before sending a PvP invite.");
            return;
        }

        if (currentUser.getSavedParties().isEmpty()) {
            append("You must have at least one saved party before sending a PvP invite.");
            return;
        }

        String opponent = JOptionPane.showInputDialog(this, "Enter opponent username:");
        if (opponent == null || opponent.isBlank()) {
            append("PvP invite cancelled.");
            return;
        }

        if (!"dave".equals(opponent)) {
            append("Opponent profile not found.");
            return;
        }

        if (demoOpponent.getSavedParties().isEmpty()) {
            append("Opponent does not have a saved party.");
            return;
        }

        lastInvite = pvpService.sendInvitation(currentUser.getUsername(), opponent);
        append("PvP invitation sent from " + currentUser.getUsername() + " to " + opponent + ".");
        append("Invite status: " + lastInvite.getStatus());
        append("Requirement check passed: both players have at least one saved party.");
    }

    private void acceptPvPInvite() {
        if (lastInvite == null) {
            append("No PvP invitation has been sent yet.");
            return;
        }

        try {
            lastInvite = pvpService.acceptInvitation(lastInvite.getId());
            append("Invitation accepted automatically.");
            append("Invite status: " + lastInvite.getStatus());
        } catch (Exception ex) {
            append("Accept invitation failed: " + ex.getMessage());
        }
    }

    private Party chooseSavedParty(User user, String label) {
        if (user.getSavedParties().isEmpty()) {
            return null;
        }

        String[] options = new String[user.getSavedParties().size()];
        for (int i = 0; i < user.getSavedParties().size(); i++) {
            options[i] = label + " Party " + (i + 1);
        }

        String selected = (String) JOptionPane.showInputDialog(
                this,
                "Choose a saved party for " + label + ":",
                "Select Saved Party",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (selected == null) {
            return null;
        }

        int index = Integer.parseInt(selected.substring(selected.lastIndexOf(' ') + 1)) - 1;
        return user.getSavedParties().get(index);
    }

    private void startPvPBattle() {
        if (currentUser == null) {
            append("Please log in first.");
            return;
        }

        if (lastInvite == null || !"ACCEPTED".equalsIgnoreCase(lastInvite.getStatus())) {
            append("PvP invite must be accepted before battle can start.");
            return;
        }

        Party challengerParty = chooseSavedParty(currentUser, currentUser.getUsername());
        Party opponentParty = chooseSavedParty(demoOpponent, demoOpponent.getUsername());

        if (challengerParty == null || opponentParty == null) {
            append("PvP battle cancelled.");
            return;
        }

        BattleController controller = new BattleController(new BattleEngine(new BasicDamageStrategy()));
        BattleState state = controller.start(challengerParty, opponentParty);

        while (!state.isFinished()) {
            controller.act(state, new Action(0, 0, ActionType.ATTACK));
        }

        append("PvP battle started successfully.");
        append("PvP battle phase: " + state.getPhase().name());
        append("Challenger party selected.");
        append("Opponent party selected.");
        append("PvP result: " + state.getResult());

        if ("PLAYER_WINS".equals(state.getResult())) {
            currentUser.recordWin();
            demoOpponent.recordLoss();
        } else {
            currentUser.recordLoss();
            demoOpponent.recordWin();
        }

        append("League standings updated.");
        showStandings();
    }

    private void showStandings() {
        append("=== League Standings ===");
        leagueUsers.stream()
                .sorted(Comparator.comparingInt(User::getWins).reversed()
                        .thenComparingInt(User::getBestScore).reversed())
                .forEach(user -> append(user.getUsername()
                        + " | Wins: " + user.getWins()
                        + " | Losses: " + user.getLosses()
                        + " | Best Score: " + user.getBestScore()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DemoLauncher launcher = new DemoLauncher();
            launcher.setVisible(true);
        });
    }
}
