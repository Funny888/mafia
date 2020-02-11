const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.SendCommonMessage = functions.https.onRequest(async (request, response) => {
   
    // request.on('end',test => {
    //     response.send("date => " +  test);
    // })
   
    const id = admin.firestore().collection("/Rooms/CommonChat/Messages").listDocuments();
    id.then(
        result => {
            const doc = admin.firestore().doc("/Rooms/CommonChat/Messages/Message" + (result.length + 1));
            
            doc.create(request.body).then(t => {
                response.send("hi  " + t);
            });
            },
        error => {response.send("crash" + error)}
        );
});