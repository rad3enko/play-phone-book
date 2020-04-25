# play-phone-book
A simple phone book where contacts are local `json` files.
- Language: [Scala](https://www.scala-lang.org)
- Web: [Play Framework](https://playframework.com)
## Features
- Create new contact with *name*, *phone*, *email*.
- Get contact by *id*
- Get all contacts

## API
### Import Postman request collection

Postman [request collection](https://www.postman.com/collections/ed71c7447432dbf35e19)

---

### Model
**Contact**:
```json
{
  "id": "uuid",
  "name": "name value",
  "phone": "123",
  "email": "a@b.c"
}
```
---
### Create new contact
#### `POST`  `/contacts/create`

***form-data*** ->

<- **Contact**
```
form-data = {
    "name": Option[String],
    "phone": Option[String],
    "email": Option[String],
}
```
---
### Get existing contact by *id*
#### `GET` `/contacts`
<- **Contact**
```
Query Params = {
    "id": String
}
```
---
### Get all contacts
#### `GET` `/contacts/list`
<- List[**Contact**]