redis.call('ZREMRANGEBYSCORE', KEYS[1], ARGV[1], ARGV[2])
redis.call('ZADD', KEYS[1], ARGV[3], ARGV[4])
redis.call('EXPIRE', KEYS[1], ARGV[5])
return redis.call('ZCARD', KEYS[1])