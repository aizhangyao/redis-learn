---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by zhangyao.
--- DateTime: 2023/5/17 16:05
---
local keys, values = KEYS, ARGV

redis.call('DEL', 'aiz:test:lua:names')

for i, v in ipairs(keys) do
    local num = i % 2
    if (num == 1)
    then
        redis.call('SET', 'aiz:test:lua:names:' .. keys[i], keys[i + 1])
    end
end
return true