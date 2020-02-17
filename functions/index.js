const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();






exports.GetRoom = functions.https.onRequest(async (_req,res) => {
   const rooms = await admin.firestore().collection('/Rooms').where('isBusy','==',false).get();
   let myCount = 8;
   let myRoom = "";
   rooms.forEach(room => {
    console.log("room path-> " + room.ref.path);
    room.ref.collection('/Roles').where('isBusy','==',false).get()
        .then(freeRoles => {
            if(myCount > freeRoles.size && freeRoles.size != 0){
                myCount = freeRoles.size;
                myRoom = room.id;
                return freeRoles;
            } else {
                console.log("variable wasn't update " + myCount);
                return "noting";
            }
        }).then(freeRoles => {
            if(myRoom != ""){        
                freeRoles.query.where('isBusy','==',false).get().then(roles => {
                var rand = 0;
                if(roles.size >= 2) {
                    rand = Math.floor(Math.random() * roles.size);
                }
                console.log("my room is " + myRoom  + " rand -> " + rand);
                   i = 0;
                   roles.forEach(role => {
                       if(i == rand){
                           res.send(role.data());
                        }
                        i++;
                   });
               });
                
            } else {
                res.send("All rooms are busy");
            }
        });
    });

    // function requestRole(freeRoles){
   
    // };
});