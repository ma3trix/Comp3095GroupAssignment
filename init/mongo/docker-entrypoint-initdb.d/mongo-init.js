print('START');

db = db.getSiblingDB('user-service');

db.createuser(
    {
        user: 'mongoadmin',
        password: 'password',
        roles: [{ role: 'readwrite', db: 'user-service'}],
    }
);

db.createCollection('user');

print('END');
