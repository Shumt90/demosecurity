###### **This project for demonstration securing static site and java backend**

**Status**:

develop

**Components**
- openresty https://openresty.org/
- spring security
- static site
- java spring backed

**external lua lib**:
 - https://github.com/SkyLothar/lua-resty-jwt
 - https://github.com/cloudflare/lua-resty-cookie
 
**Flow**:

implemented in lib/finch/wrap.lua file

- request to site or backend
- check jwt token in cookies. Expiration date and signature.
- redirect to login page if not valid