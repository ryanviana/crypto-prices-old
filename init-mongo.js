db = db.getSiblingDB("crypto_prices");
db.createUser({
  user: "root",
  pwd: "example",
  roles: [{ role: "readWrite", db: "crypto_prices" }],
});

db.createCollection("cryptoPrice");
print("Created collection 'cryptoPrice' in 'crypto_prices' database.");
