local key = KEYS[1];
local value = ARGV[1];

if redis.call("get",key)==value then
    redis.call("del",key)
    return true;
else
    return false;
end