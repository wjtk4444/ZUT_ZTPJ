# ZUT_ZTPJ
### Zadania z przedmiotu Zaawansowane Techniki Programowania w Javie

Baza danych została wykonana z użyciem technologii postgresql. Instrukcja importu bazy z poziomu terminala:
```
>psql create database workers;
>psql workers < ZUT_ZTPJ/database/workers.db;
```
Adres, nazwa użytkownika oraz hasło są zapisane "na sztywno" w pliku [ConnectionPool.java](ZTPJ/src/main/java/dao/ConnectionPool.java)

#### Schemat bazy danych (wszystkie id są ustawione na auto increment):
```
 Table "public.positions"
 Column |       Type        | Collation | Nullable |                
--------+-------------------+-----------+----------+
 id     | integer           |           | not null | 
 name   | character varying |           | not null |


 Table "public.workers"
       Column        |       Type        | Collation | Nullable |              
---------------------+-------------------+-----------+----------+
 id                  | integer           |           | not null |
 first_name          | character varying |           | not null | 
 last_name           | character varying |           | not null | 
 pesel               | character varying |           | not null | 
 position_id         | integer           |           | not null | 
 phone_number        | character varying |           |          | 
 service_card_number | character varying |           |          | 
 salary              | integer           |           | not null | 


 Table "public.directors_allowances"
       Column       |  Type   | Collation | Nullable |                      
--------------------+---------+-----------+----------+
 id                 | integer |           | not null |
 worker_id          | integer |           | not null | 
 business_allowance | integer |           | not null | 
 monthly_cost_limit | integer |           | not null |
 
 Table "public.tradesmans_commissions"
          Column          |  Type   | Collation | Nullable |
--------------------------+---------+-----------+----------+
 id                       | integer |           | not null |
 worker_id                | integer |           | not null | 
 commission               | integer |           | not null | 
 monthly_commission_limit | integer |           | not null | 

```

# Postęp prac:
#### Lab2:
- [x] lista pracowników
- [x] dodawanie pracownika
- [x] usuwanie pracownika
- [x] kopia zapasowa
- [x] kopia zapasowa z obsługą zip
- [x] kopia zapasowa z obsługą gzip

#### Lab3:
- [x] pobieranie danych z sieci
