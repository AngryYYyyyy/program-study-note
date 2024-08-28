--- 获取 方法中的参数: key，value ,时间
local key = KEYS[1]
local value = KEYS[2]

local expire = ARGV[1]

if redis.call("get", key) == false then
    if redis.call("set",key,value) then
        if tonumber(expire) > 0 then
            redis.call("expire",key,expire);
        end
        return true;
    end
    return false;

else
    return false;
end