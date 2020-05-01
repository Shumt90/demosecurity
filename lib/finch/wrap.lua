local ck = require "resty.cookie";
local jwt = require "resty.jwt";
local _M = {}

function _M.checkJwtToken(secret, token_live_millis, login_page)

    local cookie, err = ck:new();
    local login = false;

    if cookie then
        local jwt_token, err = cookie:get("Authorization");
        if jwt_token then


            local jwt_obj = jwt:verify(secret, jwt_token);
            if jwt_obj.verified == true then

                if jwt_obj.payload and jwt_obj.payload.iat then
                    if os.time() - jwt_obj.payload.iat < token_live_millis then
                        login = true;
                    end;
                end;
            end;
        end;
    end;

    if login == false then
        ngx.redirect(login_page);
        return;
    end;

    ngx.say("Great, you are logged"); --TODO how to return .html
end

return _M