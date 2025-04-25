CREATE TABLE "team-players" (
    id UUID NOT NULL,
    team_id UUID NOT NULL,
    player_id UUID NOT NULL,
    position VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE "team-players"
    ADD CONSTRAINT FK_team_player_to_team
        FOREIGN KEY (team_id)
        REFERENCES teams (id);

ALTER TABLE "team-players"
    ADD CONSTRAINT FK_team_player_to_user
        FOREIGN KEY (player_id)
        REFERENCES users (id);