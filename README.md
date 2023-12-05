Старт - docker compose up  
Стоп - docker compose down  
Апи точка - POST /api/v1/char-frequency  
Входные данные - json с полем "text", принимает только английские буквы (любого регистра)   
Исходящие данные - список json, отсортированный по убыванию количества вхождений символа в строку "text"       
Пример правильного запроса:   
`curl -s -X POST -d '{"text": "aaaaabcccc"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/v1/char-frequency`



