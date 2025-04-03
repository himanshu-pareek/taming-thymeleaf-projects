CREATE TABLE teams (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    coach_id UUID NOT NULL,
    version BIGINT NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE teams ADD CONSTRAINT fk_team_to_user FOREIGN KEY (coach_id) REFERENCES users(id);