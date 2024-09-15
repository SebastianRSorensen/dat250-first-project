Report for setting up frontend:

This report will be relative relative short and simple. My main issue was that i lacked support in the backend to fullfil wanted features in the frontend. Therefore i had to go back and forth altering my backend to fit my new needs. Clearly I should have spent more time figuring out what I needed, earlier on in the process...

I still need to find a better way to update the votes after a user has casted a vote, but I will solve this at the same time I solve how to better store votes directly related to a logged in user and his/her votes. Revalidating cache after voting using tanstack is relatively simply, but i need the correct data from the backend to make it work. This is what i do when a user adds a new poll, I just need to support it for votes also. Creating a field for the name and surname upon user creation would also be a good idea to actually greet the user in a nice way.

Link to code:
https://github.com/SebastianRSorensen/dat250-first-project
