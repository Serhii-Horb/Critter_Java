INSERT INTO critters (id, age, mood, name, power_level, size, type)
VALUES (gen_random_uuid(), 500, 'HAPPY', 'Firewing', 9000, 'LARGE', 'DRAGON'),
       (gen_random_uuid(), 200, 'ANGRY', 'Skyflare', 7500, 'MEDIUM', 'PHOENIX'),
       (gen_random_uuid(), 300, 'SAD', 'Shadowclaw', 8500, 'LARGE', 'GRIFFIN'),
       (gen_random_uuid(), 120, 'HAPPY', 'Silvershine', 6000, 'MEDIUM', 'UNICORN'),
       (gen_random_uuid(), 50, 'ANGRY', 'Brightfeather', 5000, 'SMALL', 'PHOENIX');
