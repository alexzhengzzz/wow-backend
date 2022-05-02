local n = tonumber(ARGV[1])
local key = KEYS[1]
local couponInfo = redis.call("HMGET",key,"totalCount")
local total = tonumber(couponInfo[1])
if not total then
    return "-500"
end
if total > 0 then
    local ret = redis.call("HINCRBY",key,"totalCount",-n)
-- remain count
    return tostring(ret)
end
return "-100" -- no coupon
