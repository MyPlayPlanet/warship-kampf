# WarShip-Kampf
This is a plugin to manage simple warship fights, as MrCreativeIV invented them. 

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4fa41f00eb01485ebf33e221b2c54d2f)](https://app.codacy.com/app/Butzlabben/warship-kampf?utm_source=github.com&utm_medium=referral&utm_content=Butzlabben/warship-kampf&utm_campaign=Badge_Grade_Dashboard)
[![Build Status](https://travis-ci.org/Butzlabben/warship-kampf.svg?branch=master)](https://travis-ci.org/Butzlabben/warship-kampf) [![](https://jitpack.io/v/Butzlabben/warship-kampf.svg)](https://jitpack.io/#Butzlabben/warship-kampf)
## Installation
1. Stop your server
2. Move the jar in the plugins folder
3. Start the server
4. Create a region in WorldGuard named "arena"
5. Set up your arena with the "/ws setup" command
## Dependencies
- WorldGuard
- FastAsyncWorldEdit
- Serverversion >= 1.13
- **IMPORTANT: THIS PLUGIN DOES NOT WORK WITH WORLDEDIT. PLEASE USE ONLY FAWE** 
## Commands
- /wsk
- /wsk team
- /wsk team put
- /wsk team remove
- /wsk team captain
- /wsk team leave
- /wsk team invite
- /wsk team accept
- /wsk team decline
- /wsk setup load
- /wsk setup save
- /wsk setup name
- /wsk setup spawn
- /wsk setup spectatorspawn
- /wsk setup waterheight
- /wsk setup pos1
- /wsk setup pos2
- /wsk setup world
- /wsk arena info
- /wsk arena start
- /wsk arena stop
- /wsk arena draw
- /wsk arena win
- /wsf role
## API
To interact with ease with WSK, just use the net.myplayplanet.wsk.api.WskAPI class
With this class it is easy to manage fights.

There are some events, which will be useful for interfering with WSK:
- ArenaStateChangeEvent
- TeamAddMemberArenaEvent
- TeamCaptainRemoveArenaEvent
- TeamCaptainSetArenaEvent
- TeamDrawEvent
- TeamLoseEvent
- TeamMemberDieEvent
- TeamRemoveMemberEvent
- TeamWinEvent
