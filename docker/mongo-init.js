db = db.getSiblingDB('vertx_demo');
db.createUser(
  {
    user: 'vertx',
    pwd: 'vertx',
    roles: [{ role: 'readWrite', db: 'vertx_demo' }],
  },
);

db = db.getSiblingDB('vertx_demo_test');
db.createUser(
  {
    user: 'vertx',
    pwd: 'vertx',
    roles: [{ role: 'readWrite', db: 'vertx_demo_test' }],
  },
);
db.createCollection('product');
db.createCollection('sales');
