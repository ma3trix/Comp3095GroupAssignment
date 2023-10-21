print('START');

// Create a user for the 'user-service' database
db = db.getSiblingDB('user-service');
db.createUser({
    user: 'mongoadmin',
    pwd: 'password',
    roles: [{ role: 'readWrite', db: 'user-service' }],
});

// Create a collection for the 'user-service' database (if needed)
db.createCollection('user');

// Switch to the 'post-service' database
db = db.getSiblingDB('post-service');
db.createUser({
    user: 'mongoadmin',
    pwd: 'password',
    roles: [{ role: 'readWrite', db: 'post-service' }],
});

// Create collections for the 'post-service' database (if needed)
db.createCollection('post');

// Switch to the 'comment-service' database
db = db.getSiblingDB('comment-service');
db.createUser({
    user: 'mongoadmin',
    pwd: 'password',
    roles: [{ role: 'readWrite', db: 'comment-service' }],
});

// Create collections for the 'comment-service' database (if needed)
db.createCollection('comment');

// Switch to the 'friendship-service' database (PostgreSQL, so no user creation needed)

print('END');
