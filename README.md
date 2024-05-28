# DeutscheBankCodingAssignment
**Requirements

To create a prototype of a platform for running blind auctions (https://en.wikipedia.org/wiki/First-price_sealed-bid_auction).

Buyers are shown products that they can bid for, and a minimum price is set by the seller. The buyer can submit any number of bids, which will be rejected if they fall below the seller's minimum price.  At the end of the auction, the first buyer who bid the highest amount will win the auction and purchase the product. 

GDPR requires that PII (TODO) is stored through an independent “Users” server, so user records for Sellers and Buyers must follow that principal. The use-cases which identify users should store only a token (issued by the “Users” server). These tokens should be validated by the “Auction Server” when processing user requests.

**Use-Cases
•	As a Seller, I want to be able to register a new product for auction and specify a minimum bid
•	As a Buyer, I want to bid in an auction any number of times
•	As a Seller, I want to be able to end the action and see the winner and their bid

**Architecture



