% Handle multiple options in the same message
option_list --> option.
option_list --> option, option_list.

option --> put_option.
option --> call_option.
option --> put_spread_option.
option --> call_spread_option.
option --> straddle_option.
option --> fence_option.
option --> fly_option.
option --> strangle_option.
option --> calendar_spread_option.
option --> composite_calendar_spread_option.
option --> flat_call_option.

% Common
% Broker A, Broker C (optional_prefix for Broker C only)
call_option --> optional_prefix, product, option_type, exercise_type, expiry_date, strike_price, moneyness, call_kw, premium, spread.
put_option --> optional_prefix, product, option_type, exercise_type, expiry_date, strike_price, put_kw, premium, spread.

call_spread_option --> optional_prefix, product, option_type, exercise_type, expiry_date, call_put_strike_prices, call_spread_kw, premium, spread.
call_spread_option --> optional_prefix, product, option_type, exercise_type, expiry_date, call_put_strike_prices, buy_sell_ratio, call_spread_kw, premium, spread.
put_spread_option --> optional_prefix, product, option_type, exercise_type, expiry_date, call_put_strike_prices, put_spread_kw, premium, spread.
put_spread_option --> optional_prefix, product, option_type, exercise_type, expiry_date, call_put_strike_prices, buy_sell_ratio, put_spread_kw, premium, spread.

fence_option --> optional_prefix, product, option_type, exercise_type, expiry_date, call_put_strike_prices, fence_kw, premium, spread, bar.
fence_option --> optional_prefix, product, option_type, exercise_type, expiry_date, call_put_strike_prices, buy_sell_ratio, fence_kw, premium, spread, bar.

% Broker A
straddle_option --> product, exercise_type, expiry_date, strike_price, straddle_kw, spread.

% Broker C
% TODO: What do these mean?
optional_prefix --> improvement.
optional_prefix --> recross.
optional_prefix --> [].
improvement --> ['improvement'].
recross --> ['***'], ['recross'], ['***'].

% Broker B 
call_option --> expiry_date, strike_price, call_kw, versus, premium, spread.
put_option --> expiry_date, strike_price, put_kw, versus, premium, spread, delta.
put_option --> expiry_date, strike_price, put_kw, versus, premium, spread, junk.

put_spread_option --> expiry_date, call_put_strike_prices, versus, premium, spread.

call_spread_option --> broker_m, expiry_date, call_put_strike_prices, buy_sell_ratio, versus, premium, spread, vernacular.
call_spread_option --> trade_type, expiry_date, call_put_strike_prices, call_spread_kw, versus, premium, spread, delta, vernacular.

flat_call_option --> expiry_date, flat_kw, call_kw, spread.

fence_option --> broker_m, expiry_date, call_put_strike_prices, fence_kw, versus, premium, spread.

broker_m --> ['electro'], ['m'].

% TODO: This is very broad and is likely to lead to ambiguous results, I think we will need a resolver to deal accurately with these
junk --> [X], { =(X, X) }.
junk --> [].

calendar_spread_option --> trade_type, product, calendar_spread_option_kw, spread_expiry_date, call_put_strike_prices, put_spread_kw, calendar_spread_option_kw, spread.
calendar_spread_option --> trade_type, product, calendar_spread_option_kw, spread_expiry_date, call_put_strike_prices, put_spread_kw, spread.
calendar_spread_option --> spread_expiry_date, call_or_put_price, spread.
calendar_spread_option --> trade_type, spread_expiry_date, strike_price, spread.

% TODO: CSO with no cal spread
calendar_spread_option --> trade_type, product, calendar_spread_option_kw, expiry_date, call_put_strike_prices, spread, vernacular.
calendar_spread_option --> trade_type, product, calendar_spread_option_kw, spread_expiry_date, strike_price, spread, vernacular.

calendar_spread_option --> broker_m, expiry_date, call_put_strike_prices, versus, premium, spread.

option --> cso_strip_block.
cso_strip_block --> trade_type, spread_expiry_date, range_separator, spread_expiry_date, strike_price, strip_kw, spread.

option --> average_price_option.
average_price_option --> trade_type, average_price_option_kw, expiry_date, strike_price, versus, premium, spread, vernacular.

% TODO: Come up with way of disregarding rest of line after stop word
vernacular --> stop_word.
vernacular --> stop_word, n_of_anything.
vernacular --> []. 

n_of_anything --> anything.
n_of_anything --> anything, n_of_anything.
anything --> [X], { =(X, X) }.

stop_word --> ['now'].
stop_word --> ['ppr'].
stop_word --> ['trades'].

straddle_option --> product,  [ 'straddle'], [ 'update'], straddle_leg_list.
fly_option --> trade_type, month, fly_strike_price, call_or_put_kw, fly_kw, versus, equivalent_future_price, spread, delta.
strangle_option --> trade_type, month, strangle_strike_price, [ 'strangle'], [ 'live'], spread.

optional_cso_prefix --> trade_type, product, cso_option_type.
optional_cso_prefix --> [].

equivalent_future_price --> number.
delta --> number, ['d'].

versus --> ['vs'].

fly_strike_price --> number, slash, number, slash, number.
strangle_strike_price --> number, slash, number.

% Shared library
straddle_leg_list --> straddle_leg.
straddle_leg_list --> straddle_leg, straddle_leg_list.
straddle_leg --> expiry_date, strike_price, spread.

put_kw --> [X], { member(X, ['p', 'put']) }.
call_kw --> [X], { member(X, ['c', 'call']) }.

call_or_put_kw --> call_kw.
call_or_put_kw --> put_kw.

spread_kw --> [X], { member(X, ['spread', 'sprd']) }.
straddle_kw --> ['straddle'].
fence_kw --> ['fence'].
fly_kw --> ['fly'].
strip_kw --> [ 'strip'].

put_spread_kw --> [X], { member(X, ['ps', 'puts']) }.
put_spread_kw --> put_kw, spread_kw.
call_spread_kw --> [X], {member(X, ['cs', 'calls']) }.
call_spread_kw --> call_kw, spread_kw.

flat_kw --> ['flat'].

expiry_date --> date.
spread_date --> from_date, dash, to_date.
spread_date --> from_date, to_date.
spread_date --> expiry_date.
from_date --> date.
to_date --> date.

spread_expiry_date --> long_month, slash, short_month.
long_month --> month.
short_month --> month.

date --> month, year.
date --> month.
date --> quarter.
date --> quarter, year.
date --> quarter_year.
date --> quarter, dash, year.
date --> half.
date --> half, year.
date --> ['cal'], year.
month --> [M], { member(M, ['jan', 'feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug', 'sep', 'sept', 'oct', 'nov', 'dec', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'q', 'u', 'v', 'x', 'z']) }. 
quarter --> ['q'], [N], { member(N, [1, 2, 3, 4]) }.
quarter --> [N], ['q'], { member(N, [1, 2, 3, 4]) }.
quarter_year --> ['q'], [N], { member(N, [310, 410, 111, 211, 311, 411]) }. % TODO: Break this out into Tree and consider all options
half --> [N], ['h'], { member(N, [1, 2]) }.
year --> [Y], { member(Y, [10, 11, 12, 13, 14]) }.

strike_price --> number.
strike_price --> call_price.
strike_price --> put_price.

call_put_strike_prices --> call_or_put_price, slash, call_or_put_price.
call_or_put_price --> number.
call_or_put_price --> call_price.
call_or_put_price --> put_price.
call_price --> number, call_kw.
put_price --> number, put_kw.

premium --> ['vs'], premium_value. % TODO: Is this part of the value?
premium --> ['x'], premium_value.
premium --> premium_value.
premium_value --> number.

spread --> bid, slash, offer.
bid --> number.
offer --> number.

%moneyness --> [X], { member(X, ['atm', 'otm', 'itm']) }.
moneyness --> ['atm'].
moneyness --> [].

product --> product_kw.

product_kw --> ['brent'].
product_kw --> ['brt'].
product_kw --> ['crude'].
product_kw --> ['gasoil'].
product_kw --> ['wti'].
product_kw --> ['fuel'], [3.5], pc, ['barges'].

exercise_type --> exercise_kw.
exercise_type --> [].
exercise_kw --> ['american'].

option_type --> calendar_spread_option_kw.
option_type --> average_price_option_kw.
option_type --> ['('], ['lo'], [')']. % Listed option
option_type --> [].

average_price_option_kw --> ['apo'].
calendar_spread_option_kw --> ['cso'].

trade_type --> ['otc'].
%TODO trade_type --> physical
%TODO trade_type --> Exchange 

buy_sell_ratio --> buy, by, sell.
buy --> number.
sell --> number.
by --> ['*'].
by --> ['x'].

bar --> ['('], number, ['put'], [')']. % TODO: What is bar?
bar --> ['('], ['cp'], [')'].
bar --> ['('], ['pp'], [')'].
bar --> [].

number --> [N], { number(N) }.

range_separator --> dash.
range_separator --> slash.

slash --> ['/'].
dash --> ['-'].
pc --> ['%'].
