package lowsw.domain;

import lowsw.service.ActionType;

public record Action(int actorIndex, int targetIndex, ActionType type) { }
