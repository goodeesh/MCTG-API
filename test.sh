#!/bin/sh
# Truncate all tables
docker exec -it mtcgdb psql -U postgres -d mydb -c "
    TRUNCATE
        cards,
        history,
        lobby,
        packages,
        sessions,
        tradings,
        users
    RESTART IDENTITY CASCADE;
"
# --------------------------------------------------
# Monster Trading Cards Game
# --------------------------------------------------
echo "CURL Testing for Monster Trading Cards Game"
echo .

# --------------------------------------------------
echo -e "\n******************************"
echo "1) Create Users (Registration)"
echo "******************************"
#Create User
curl -i -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"kienboec\", \"Password\":\"daniel\"}"
echo .
curl -i -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"altenhof\", \"Password\":\"markus\"}"
echo .
curl -i -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"admin\",    \"Password\":\"istrator\"}"
echo .

read -p "Press any key to resume ..." null

echo "should fail:"
curl -i -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"kienboec\", \"Password\":\"daniel\"}"
echo .
curl -i -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"kienboec\", \"Password\":\"different\"}"
echo . 
echo .

read -p "Press any key to resume ..." null

# --------------------------------------------------

echo -e "\n******************************"
echo "2) Login"
echo "******************************"
curl -i -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"kienboec\", \"Password\":\"daniel\"}"
echo .
curl -i -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"altenhof\", \"Password\":\"markus\"}"
echo .
curl -i -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"admin\",    \"Password\":\"istrator\"}"
echo .

read -p "Press any key to resume ..." null

echo "should fail:"
curl -i -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"kienboec\", \"Password\":\"different\"}"
echo .
echo .

read -p "Press any key to resume ..." null

echo -e "\n******************************"
echo "3) create packages and buy them in order, to ensure card ownership and configure deck later works (done by admin)"
echo "******************************"

echo -e "\n******************************"
echo "3.1) create package"
echho "******************************"
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"Id\":\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"Name\":\"Dragon\", \"Damage\": 50.0}, {\"Id\":\"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"Name\":\"WaterSpell\", \"Damage\": 20.0}, {\"Id\":\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"Name\":\"Ork\", \"Damage\": 45.0}, {\"Id\":\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\", \"Name\":\"FireSpell\",    \"Damage\": 25.0}]"

echo -e "\n******************************"
echo "3.2) adquire package kienboec"
echo "******************************"
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d ""

echo -e "\n******************************"
echo "3.3) show unconfigured deck kienboec and fail because of only 3 cards"
echo "******************************"

curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer kienboec-mtcgToken"
echo .
echo .
curl -i -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "[\"aa9999a0-734c-49c6-8f4a-651864b14e62\", \"d6e9c720-9b5a-40c7-a6b2-bc34752e3463\", \"d60e23cf-2238-4d49-844f-c7589ee5342e\"]"

echo -e "\n******************************"
echo "3.4) configure deck kienboec"
echo "******************************"
curl -i -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "[\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\"]"

echo -e "\n******************************"
echo "3.5) create + buy + configure package altenhof"
echo "******************************"

curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"Id\":\"644808c2-f87a-4600-b313-122b02322fd5\", \"Name\":\"WaterGoblin\", \"Damage\":  9.0}, {\"Id\":\"4a2757d6-b1c3-47ac-b9a3-91deab093531\", \"Name\":\"Dragon\", \"Damage\": 55.0}, {\"Id\":\"91a6471b-1426-43f6-ad65-6fc473e16f9f\", \"Name\":\"WaterSpell\", \"Damage\": 21.0}, {\"Id\":\"4ec8b269-0dfa-4f97-809a-2c63fe2a0025\", \"Name\":\"Ork\", \"Damage\": 55.0}, {\"Id\":\"f8043c23-1534-4487-b66b-238e0c3c39b5\", \"Name\":\"WaterSpell\",   \"Damage\": 23.0}]"
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d ""
curl -i -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "[\"644808c2-f87a-4600-b313-122b02322fd5\", \"4a2757d6-b1c3-47ac-b9a3-91deab093531\", \"91a6471b-1426-43f6-ad65-6fc473e16f9f\", \"4ec8b269-0dfa-4f97-809a-2c63fe2a0025\"]"

echo -e "\n******************************"
echo "Now testing of battle will be possible later"
echo "******************************"

read -p "Press any key to resume ..." null

echo -e "\n******************************"
echo "4) create more packages (done by admin)"
echo "******************************"

curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"Id\":\"b017ee50-1c14-44e2-bfd6-2c0c5653a37c\", \"Name\":\"WaterGoblin\", \"Damage\": 11.0}, {\"Id\":\"d04b736a-e874-4137-b191-638e0ff3b4e7\", \"Name\":\"Dragon\", \"Damage\": 70.0}, {\"Id\":\"88221cfe-1f84-41b9-8152-8e36c6a354de\", \"Name\":\"WaterSpell\", \"Damage\": 22.0}, {\"Id\":\"1d3f175b-c067-4359-989d-96562bfa382c\", \"Name\":\"Ork\", \"Damage\": 40.0}, {\"Id\":\"171f6076-4eb5-4a7d-b3f2-2d650cc3d237\", \"Name\":\"RegularSpell\", \"Damage\": 28.0}]"
echo .																																																																																		 				    
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"Id\":\"ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"Id\":\"65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5\", \"Name\":\"Dragon\", \"Damage\": 50.0}, {\"Id\":\"55ef46c4-016c-4168-bc43-6b9b1e86414f\", \"Name\":\"WaterSpell\", \"Damage\": 20.0}, {\"Id\":\"f3fad0f2-a1af-45df-b80d-2e48825773d9\", \"Name\":\"Ork\", \"Damage\": 45.0}, {\"Id\":\"8c20639d-6400-4534-bd0f-ae563f11f57a\", \"Name\":\"WaterSpell\",   \"Damage\": 25.0}]"
echo .																																																																																		 				    
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"Id\":\"d7d0cb94-2cbf-4f97-8ccf-9933dc5354b8\", \"Name\":\"WaterGoblin\", \"Damage\":  9.0}, {\"Id\":\"44c82fbc-ef6d-44ab-8c7a-9fb19a0e7c6e\", \"Name\":\"Dragon\", \"Damage\": 55.0}, {\"Id\":\"2c98cd06-518b-464c-b911-8d787216cddd\", \"Name\":\"WaterSpell\", \"Damage\": 21.0}, {\"Id\":\"951e886a-0fbf-425d-8df5-af2ee4830d85\", \"Name\":\"Ork\", \"Damage\": 55.0}, {\"Id\":\"dcd93250-25a7-4dca-85da-cad2789f7198\", \"Name\":\"FireSpell\",    \"Damage\": 23.0}]"
echo .																																																																																		 				    
curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"Id\":\"b2237eca-0271-43bd-87f6-b22f70d42ca4\", \"Name\":\"WaterGoblin\", \"Damage\": 11.0}, {\"Id\":\"9e8238a4-8a7a-487f-9f7d-a8c97899eb48\", \"Name\":\"Dragon\", \"Damage\": 70.0}, {\"Id\":\"d60e23cf-2238-4d49-844f-c7589ee5342e\", \"Name\":\"WaterSpell\", \"Damage\": 22.0}, {\"Id\":\"fc305a7a-36f7-4d30-ad27-462ca0445649\", \"Name\":\"Ork\", \"Damage\": 40.0}, {\"Id\":\"84d276ee-21ec-4171-a509-c1b88162831c\", \"Name\":\"RegularSpell\", \"Damage\": 28.0}]"
echo .
echo .

read -p "Press any key to resume ..." null

# --------------------------------------------------
echo -e "\n******************************"
echo "5) acquire more packages kienboec"
echo "******************************"

curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d ""
echo .
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d ""
echo .
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d ""
echo .
echo "should fail (no money):"
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d ""
echo .
echo .

read -p "Press any key to resume ..." null

# --------------------------------------------------
echo -e "\n******************************"
echo "6) acquire packages altenhof"
echo "******************************"

curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d ""
echo .
echo "should fail (no package):"
curl -i -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d ""
echo .
echo .

read -p "Press any key to resume ..." null

# --------------------------------------------------
echo -e "\n******************************"
echo "7) show all acquired cards kienboec"
echo "******************************"

curl -i -X GET http://localhost:10001/cards --header "Authorization: Bearer kienboec-mtcgToken"
echo "should fail (no token)"
curl -i -X GET http://localhost:10001/cards 
echo .
echo .

read -p "Press any key to resume ..." null

# --------------------------------------------------
echo -e "\n******************************"
echo "8) show all acquired cards altenhof"
echo "******************************"

curl -i -X GET http://localhost:10001/cards --header "Authorization: Bearer altenhof-mtcgToken"
echo .
echo .

read -p "Press any key to resume ..." null

# --------------------------------------------------
echo -e "\n******************************"
echo "9) show configured deck"
echo "******************************"

curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer kienboec-mtcgToken"
echo .
curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer altenhof-mtcgToken"
echo .
echo .

read -p "Press any key to resume ..." null

# --------------------------------------------------
echo -e "\n******************************"
echo "10) edit user data"
echo "******************************"

echo .
curl -i -X GET http://localhost:10001/users/kienboec --header "Authorization: Bearer kienboec-mtcgToken"
echo .
curl -i -X GET http://localhost:10001/users/altenhof --header "Authorization: Bearer altenhof-mtcgToken"
echo .
curl -i -X PUT http://localhost:10001/users/kienboec --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "{\"Name\": \"Kienboeck\",  \"Bio\": \"me playin...\", \"Image\": \":-)\"}"
echo .
curl -i -X PUT http://localhost:10001/users/altenhof --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "{\"Name\": \"Altenhofer\", \"Bio\": \"me codin...\",  \"Image\": \":-D\"}"
echo .
curl -i -X GET http://localhost:10001/users/kienboec --header "Authorization: Bearer kienboec-mtcgToken"
echo .
curl -i -X GET http://localhost:10001/users/altenhof --header "Authorization: Bearer altenhof-mtcgToken"
echo .
echo .

read -p "Press any key to resume ..." null

# --------------------------------------------------
echo -e "\n******************************"
echo "should fail edit user data:"
echo "******************************"
curl -i -X GET http://localhost:10001/users/altenhof --header "Authorization: Bearer kienboec-mtcgToken"
echo .
curl -i -X GET http://localhost:10001/users/kienboec --header "Authorization: Bearer altenhof-mtcgToken"
echo .
curl -i -X PUT http://localhost:10001/users/kienboec --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "{\"Name\": \"Hoax\",  \"Bio\": \"me playin...\", \"Image\": \":-)\"}"
echo .
curl -i -X PUT http://localhost:10001/users/altenhof --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "{\"Name\": \"Hoax\", \"Bio\": \"me codin...\",  \"Image\": \":-D\"}"
echo .
curl -i -X GET http://localhost:10001/users/someGuy  --header "Authorization: Bearer kienboec-mtcgToken"
echo .
echo .

read -p "Press any key to resume ..." null

# --------------------------------------------------
echo -e "\n******************************"
echo "11) show stats"
echo "******************************"
curl -i -X GET http://localhost:10001/stats --header "Authorization: Bearer kienboec-mtcgToken"
echo .
curl -i -X GET http://localhost:10001/stats --header "Authorization: Bearer altenhof-mtcgToken"
echo .
echo .

read -p "Press any key to resume ..." null

# --------------------------------------------------
echo -e "\n******************************"
echo "12) show scoreboard"
echo "******************************"

curl -i -X GET http://localhost:10001/scoreboard --header "Authorization: Bearer kienboec-mtcgToken"
echo .
echo .

read -p "Press any key to resume ..." null

# --------------------------------------------------
echo -e "\n******************************"
echo "13) battle"
echo "******************************"
curl -i -X POST http://localhost:10001/battles --header "Authorization: Bearer kienboec-mtcgToken" &
curl -i -X POST http://localhost:10001/battles --header "Authorization: Bearer altenhof-mtcgToken" &
wait

read -p "Press any key to resume ..." null

# --------------------------------------------------
echo -e "\n******************************"
echo "14) Stats after battle"
echo "******************************"
echo "kienboec"
curl -i -X GET http://localhost:10001/stats --header "Authorization: Bearer kienboec-mtcgToken"
echo .
echo "altenhof"
curl -i -X GET http://localhost:10001/stats --header "Authorization: Bearer altenhof-mtcgToken"
echo .
echo .

read -p "Press any key to resume ..." null

# --------------------------------------------------
echo -e "\n******************************"
echo "15) scoreboard after battle"
echo "******************************"
curl -i -X GET http://localhost:10001/scoreboard --header "Authorization: Bearer kienboec-mtcgToken"
echo .
echo .

read -p "Press any key to resume ..." null

# --------------------------------------------------
echo -e "\n******************************"
echo "16) trade"
echo "******************************"
echo "check trading deals"
curl -i -X GET http://localhost:10001/tradings --header "Authorization: Bearer kienboec-mtcgToken"
echo .
echo "create trading deal"
echo "should fail, card is in deck: "
curl -i -X POST http://localhost:10001/tradings --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "{\"Id\": \"6cd85277-4590-49d4-b0cf-ba0a921faad0\", \"CardToTrade\": \"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"Type\": \"monster\", \"MinimumDamage\": 15}"
echo .
echo .
echo "should succed: "
curl -i -X POST http://localhost:10001/tradings --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "{\"Id\": \"6cd85277-4590-49d4-b0cf-ba0a921faad0\", \"CardToTrade\": \"d04b736a-e874-4137-b191-638e0ff3b4e7\", \"Type\": \"monster\", \"MinimumDamage\": 15}"
echo .
echo .
read -p "Press any key to resume ..." null

echo "check trading deals"
echo .
echo .
curl -i -X GET http://localhost:10001/tradings --header "Authorization: Bearer kienboec-mtcgToken"
echo .
echo .
echo .
curl -i -X GET http://localhost:10001/tradings --header "Authorization: Bearer altenhof-mtcgToken"
echo .
echo .
echo .

read -p "Press any key to resume ..." null

echo "delete trading deal"
curl -i -X DELETE http://localhost:10001/tradings/6cd85277-4590-49d4-b0cf-ba0a921faad0 --header "Authorization: Bearer kienboec-mtcgToken"
echo .
echo .

read -p "Press any key to resume ..." null

# --------------------------------------------------
echo -e "\n******************************"
echo "17) check trading deals"
echo "******************************"
curl -i -X GET http://localhost:10001/tradings  --header "Authorization: Bearer kienboec-mtcgToken"
echo .
echo "create trading deal"
echo .
curl -i -X POST http://localhost:10001/tradings --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "{\"Id\": \"6cd85277-4590-49d4-b0cf-ba0a921faad0\", \"CardToTrade\": \"dfdd758f-649c-40f9-ba3a-8657f4b3439f\", \"Type\": \"monster\", \"MinimumDamage\": 15}"
echo -e "\n******************************"
echo "check trading deals"
echo "******************************"
curl -i -X GET http://localhost:10001/tradings  --header "Authorization: Bearer kienboec-mtcgToken"
echo .
curl -i -X GET http://localhost:10001/tradings  --header "Authorization: Bearer altenhof-mtcgToken"
echo .

read -p "Press any key to resume ..." null
echo -e "\n******************************"
echo "try to trade with yourself (should fail)"
echo "******************************"
echo .
curl -i -X POST http://localhost:10001/tradings/6cd85277-4590-49d4-b0cf-ba0a921faad0 --header "Content-Type: application/json" --header "Authorization: Bearer kienboec-mtcgToken" -d "\"4ec8b269-0dfa-4f97-809a-2c63fe2a0025\""
echo .

read -p "Press any key to resume ..." null
echo -e "\n******************************"
echo "try to trade"
echo "******************************"
echo .
curl -i -X POST http://localhost:10001/tradings/6cd85277-4590-49d4-b0cf-ba0a921faad0 --header "Content-Type: application/json" --header "Authorization: Bearer altenhof-mtcgToken" -d "\"f8043c23-1534-4487-b66b-238e0c3c39b5\""
echo .
curl -i -X GET http://localhost:10001/tradings --header "Authorization: Bearer kienboec-mtcgToken"
echo .
curl -i -X GET http://localhost:10001/tradings --header "Authorization: Bearer altenhof-mtcgToken"
echo .
echo -e "\n******************************"
echo "Special/extra feature, history route:"
curl -i -X GET http://localhost:10001/history
echo "******************************"
# --------------------------------------------------
echo "end..."