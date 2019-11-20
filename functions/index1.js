functions = require('firebase-functions');
admin = require('firebase-admin');
admin.initializeApp();

	exports.sendMessage = functions.https.onRequest((req,res) => {
	res.send({"Message":[req.query.name,req.query.time,req.query.mes]});
})
