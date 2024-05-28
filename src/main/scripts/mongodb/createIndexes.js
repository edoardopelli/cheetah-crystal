db.pins.createIndex(
    { "pin": 1 },
    { unique: true }
);

db.pins.createIndex(
    { "createdAt": 1 },
    { expireAfterSeconds: 300 }
);

db.users.createIndex(
    { "email": 1 },
    { unique: true }
);

db.users.createIndex(
    { "username": 1 },
    { unique: true }
);

db.users.createIndex(
    { "email": 1 },
    { name: "email_asc_index" }
);

db.users.createIndex(
    { "username": 1 },
    { name: "username_asc_index" }
);
db.users.createIndex(
    { "otp": 1 },
    { name: "otp_asc_index" }
);

db.pins.createIndex(
    { "pin": 1 },
    {name: "pin_asc_index"}
);

