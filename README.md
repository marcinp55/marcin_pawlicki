## Overview
Server-side code repo for recruitment project. App calculates 
contract gain in PLN in selected country. Net amount is calculated 
with assumption that every month has 22 days and every country has 
different fixed costs and taxes. Currency exchange rate is 
fetched from [NBPApi](http://api.nbp.pl/). 
App fetches latest available exchange rate at the time of request.

## Instructions
!!! Heroku application server switches to sleep mode after some inactivity time, so when the app is not working, wait a few
seconds and refresh !!!

This web app is hosted at [Heroku](https://sonalake-recruit.herokuapp.com/) though as for now it only has one endpoint 
([ExampleRequestUrl](https://sonalake-recruit.herokuapp.com/nbp/net?fixedCost=500&tax=20&dailyPay=250&currCode=GBP)). 
Live documentation is also available [here](https://sonalake-recruit.herokuapp.com/swagger-ui.html#/).

Please use the app via Angular app hosted at [Amazon WS](http://sonalake-recruit-front.s3-website.eu-west-2.amazonaws.com/).
Also please wait for Heroku to wake up if the site is not working instantly.

Repo for Angular app is [here](https://github.com/marcinp55/marcin_pawlicki_front).