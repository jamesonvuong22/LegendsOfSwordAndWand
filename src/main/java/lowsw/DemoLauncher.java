package lowsw;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import lowsw.controller.BattleController;
import lowsw.domain.Action;
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
import lowsw.service.PvPService;

public class DemoLauncher extends JFrame {

    private final JTextArea outputArea;

    private final AuthService authService;
    private final CampaignService campaignService;
    private final PvPService pvpService;
    private final DefaultHeroFactory heroFactory;

    private User currentUser;
    private BattleController battleController;
    private BattleState currentBattle;
    private lowsw.domain.CampaignState currentCampaign;
    private Party pvpPartyA;
    private Party pvpPartyB;
    private PvPInvitation lastInvite;

    public DemoLauncher() {
        setTitle("Legends of Sword and Wand Demo");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        authService = new AuthService(new InMemoryUserRepository());
        heroFactory = new DefaultHeroFactory();
        campaignService = new CampaignService(new InMemoryCampaignRepository(), heroFactory);
        pvpService = new PvPService(new InMemoryPvPRepository());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 8, 8));
        JPanel authPanel = new JPanel(new GridLayout(1, 4, 8, 8));
        JPanel gamePanel = new JPanel(new GridLayout(2, 6, 8, 8));

        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");
        JButton clearButton = new JButton("Clear Output");
        JButton showUserButton = new JButton("Show Current User");

        JButton startBattleButton = new JButton("Start Battle");
        JButton attackButton = new JButton("Attack");
        JButton defendButton = new JButton("Defend");
        JButton waitButton = new JButton("Wait");
        JButton castButton = new JButton("Cast");
        JButton startCampaignButton = new JButton("Start Campaign");
        JButton nextRoomButton = new JButton("Next Room");
        JButton exitCampaignButton = new JButton("Exit Campaign");
        JButton continueCampaignButton = new JButton("Continue Campaign");
        JButton sendInviteButton = new JButton("Send PvP Invite");
        JButton acceptInviteButton = new JButton("Accept PvP Invite");
        JButton startPvPBattleButton = new JButton("Start PvP Battle");

        registerButton.addActionListener(e -> registerUser());
        loginButton.addActionListener(e -> loginUser());
        clearButton.addActionListener(e -> outputArea.setText(""));
        showUserButton.addActionListener(e -> showCurrentUser());

        startBattleButton.addActionListener(e -> startBattle());
        attackButton.addActionListener(e -> doBattleAction(ActionType.ATTACK));
        defendButton.addActionListener(e -> doBattleAction(ActionType.DEFEND));
        waitButton.addActionListener(e -> doBattleAction(ActionType.WAIT));
        castButton.addActionListener(e -> doBattleAction(ActionType.CAST));

        startCampaignButton.addActionListener(e -> startCampaign());
        nextRoomButton.addActionListener(e -> nextRoom());
        exitCampaignButton.addActionListener(e -> exitCampaign());
        continueCampaignButton.addActionListener(e -> continueCampaign());

        sendInviteButton.addActionListener(e -> sendPvPInvite());
        acceptInviteButton.addActionListener(e -> acceptPvPInvite());
        startPvPBattleButton.addActionListener(e -> startPvPBattle());

        authPanel.add(registerButton);
        authPanel.add(loginButton);
        authPanel.add(showUserButton);
        authPanel.add(clearButton);

        gamePanel.add(startBattleButton);
        gamePanel.add(attackButton);
        gamePanel.add(defendButton);
        gamePanel.add(waitButton);
        gamePanel.add(castButton);
        gamePanel.add(startCampaignButton);
        gamePanel.add(nextRoomButton);
        gamePanel.add(exitCampaignButton);
        gamePanel.add(continueCampaignButton);
        gamePanel.add(sendInviteButton);
        gamePanel.add(acceptInviteButton);
        gamePanel.add(startPvPBattleButton);

        topPanel.add(authPanel);
        topPanel.add(gamePanel);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        append("=== Legends of Sword and Wand Demo ===");
        append("Use the buttons above to interact with the system.");
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
            append("Login successful for user: " + currentUser.getUsername());
        } else {
            append("Login failed: incorrect username or password.");
        }
    }

    private void showCurrentUser() {
        if (currentUser == null) {
            append("No user is currently logged in.");
        } else {
            append("Current user: " + currentUser.getUsername());
        }
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
                append("Battle result: " + currentBattle.getResult());
            }
        } catch (Exception ex) {
            append("Battle action failed: " + ex.getMessage());
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
    }

    private void exitCampaign() {
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
    }

    private void sendPvPInvite() {
        String challenger = JOptionPane.showInputDialog(this, "Enter challenger username:");
        if (challenger == null || challenger.isBlank()) {
            append("PvP invite cancelled.");
            return;
        }

        String opponent = JOptionPane.showInputDialog(this, "Enter opponent username:");
        if (opponent == null || opponent.isBlank()) {
            append("PvP invite cancelled.");
            return;
        }

        lastInvite = pvpService.sendInvitation(challenger, opponent);
        append("PvP invitation sent from " + challenger + " to " + opponent + ".");
        append("Invite status: " + lastInvite.getStatus());
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

    private void startPvPBattle() {
        pvpPartyA = new Party(0);
        pvpPartyA.addHero(heroFactory.createHero(ClassType.ORDER, "Knight", 1));

        pvpPartyB = new Party(0);
        pvpPartyB.addHero(heroFactory.createHero(ClassType.CHAOS, "Goblin", 1));

        BattleState state = pvpService.startBattle(pvpPartyA, pvpPartyB);
        append("PvP battle started successfully.");
        append("PvP battle phase: " + state.getPhase().name());
        append("Challenger hero: " + pvpPartyA.getHeroes().get(0));
        append("Opponent hero: " + pvpPartyB.getHeroes().get(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DemoLauncher launcher = new DemoLauncher();
            launcher.setVisible(true);
        });
    }
}
