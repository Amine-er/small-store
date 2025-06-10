db = db.getSiblingDB('reviewdb');
db.createUser({
    user: "reviewuser",
    pwd: "reviewpass",
    roles: [
        { role: "readWrite", db: "reviewdb" }
    ]
});
