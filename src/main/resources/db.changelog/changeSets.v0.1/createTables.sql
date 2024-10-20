CREATE TYPE critter_type AS ENUM ('DRAGON', 'PHOENIX', 'GRIFFIN', 'UNICORN');
CREATE TYPE critter_size AS ENUM ('SMALL', 'MEDIUM', 'LARGE');
CREATE TYPE critter_mood AS ENUM ('HAPPY', 'ANGRY', 'SAD');

CREATE TABLE critters (
                          id UUID NOT NULL,
                          age INTEGER NOT NULL,
                          mood critter_mood NOT NULL,
                          name VARCHAR(100) NOT NULL,
                          power_level INTEGER NOT NULL,
                          size critter_size NOT NULL,
                          type critter_type NOT NULL,
                          PRIMARY KEY (id)
);

