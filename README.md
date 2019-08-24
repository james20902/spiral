# spiral
## a robust robot framework... probably
### My framework is the one that will pierce the heavens! 
red_framework didn't really work out, lost motivation and ideas were convoluted. Programming style follows [this doc.](https://google.github.io/styleguide/javaguide.html)

### Goals
1. simplicity
2. efficiency
3. future-proof

It is effectively a command based rewrite including rewrites of some WPILib functions for speed, and utility additions. This allows for a plug-and-play experience for new or inexperienced teams, and will make debugging problems simpler for the more experienced.

### Motors
Adding motors is as simple as writing broken English and is done in `src/main/deploy/robot.motors`
example: `talon dl on pdp 4 can 24 encoder motor`
This means there is a talon SRX(talon) left drive motor(dl) on the PDP slot 4(on pdp 4) with CAN id 24(can 24), and the encoder device is integrated(encoder motor). you could do 2 3 after encoder for A at 2 and B at 3 on RoboRIO(example: `encoder 2 3`). You need to make sure to begin each motor group(dl/dr) with the motor with an encoder. This should get changed to autodetect eventually.

### Subsystems
Adding subsystems is very similar to motors. Currently being made.

### Pathfinding Setup
Just drop your paths in the RoboRIO `deploy` directory, which would be in your IDE under `src/main/deploy`. Tuning is currently NOT implemented, but will be done automatically soon(before 2020 kickoff, hopefully). Declare things like max velocity and wheel diameter under `src/main/deploy/robot.settings` using YAMLs style, but in interest of speed full YAML will not be supported and every setting will have to be kept in the stock order.

README IS IN PROGRESS, AS IS THE REST OF SPIRAL
