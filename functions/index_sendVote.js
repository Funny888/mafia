const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();


exports.SendVote = functions.https.onRequest(async (req, res) => {
   fieldRoom = req.body['Room'];
   voteAgainstWhom = req.body['TargetId'];
   myId = req.body['MyId'];
   const rooms = await admin.firestore().collection('/Rooms/' + fieldRoom + "/Roles").get();
   var willContinue = new Promise((resolve, reject) => {
      var notSended = true;
      rooms.docs.forEach(target => {
         if (target.data().Id == myId && target.data().Voted == true) {
            notSended = false;
         }
      });
      resolve(notSended);
   })

   willContinue.then(result => {
      if (result) {
         rooms.docs.forEach(target => {
            if (target.data().Id == myId) {
               target.ref.update("Voted", true);
               console.log("Player has voted");
            }
            if (target.data().Id == voteAgainstWhom) {
               target.ref.update("VoicesAgainst", target.data().VoicesAgainst + 1);
               console.log("Voice is against player");
            }
         })
         res.send('{"Result":"Success"}');
      } else {
         res.send('{"Result":"Vote was sended"}')
      }
   })
});