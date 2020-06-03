const functions = require('firebase-functions');
const admin = require('firebase-admin');
const Emitet = require('events');
admin.initializeApp();


exports.PartAndStopGame = functions.https.onRequest(async (req, res) => {
    var fieldRoom = req.body['Room'];
    var Flag = req.body['Flag'];
    var firebase = await admin.firestore().doc('/Rooms/' + fieldRoom);
    firebase.get().then(file => {
        if(file.data().rounds == 0 || Flag == -1) {
            file.ref.update("isBusy", false);
            file.ref.update("rounds", 5);
            file.ref.collection("Roles").get().then(roles => {
                roles.docs.forEach(role => {
                    role.ref.update("isBusy", false);
                    role.ref.update("isDead", false);
                });
            });
            res.send('{"Result":"Game is the end"}');
        } else {
            file.ref.update("rounds",file.data().rounds - 1);
            res.send('{"Result":"Leave rounds:' + (file.data().rounds - 1) + '"}')
        } 
    });
})