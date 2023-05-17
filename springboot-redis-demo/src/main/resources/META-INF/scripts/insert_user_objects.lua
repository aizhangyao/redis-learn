---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by zhangyao.
--- DateTime: 2023/5/17 16:31
---

local userList, values = KEYS, ARGV

redis.call('DEL', 'aiz:test:lua:users')

for i, v in ipairs(userList) do
    local num = i % 2
    if (num == 1)
    then
        local user = userList[i];
        local userMap = cjson.decode(user);
        local userName = userMap["name"];
        redis.call('HSET', 'aiz:test:lua:users', userName, user)

        local userStr = cjson.encode(v);
        redis.call('HSET', 'aiz:test:lua:users', userName .. i, userStr)
    end
end
return true