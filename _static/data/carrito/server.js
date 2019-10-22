const express = require('express');
const PORT = process.env.PORT || 5000;

const MAX_CARRITOS = 500;
const MAX_PRODUCTOS= 20;

// base de datos: https://github.com/typicode/lowdb
const low = require('lowdb');
const FileSync = require('lowdb/adapters/FileSync');
const adapter = new FileSync('db.json');
const db = low(adapter);

db.defaults({ carritos:[] }).write();

const app = express();

// Asume que el cuerpo del mensaje está en JSON
app.use(express.json());


// Acepta caracteres UTF-8 en la URL:
app.use( (req, res, next) => {
  req.url = decodeURI(req.url);
  next();
});


app.all('/*', function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header('Access-Control-Allow-Methods', 'DELETE, PUT, GET, POST, OPTIONS');
  res.header("Access-Control-Allow-Headers", "X-Requested-With");
  next();
});


app.get('/:carrito/productos', (req, res) => {
  const carrito= db.get('carritos').find({ nombre:req.params.carrito });
  if (!carrito.value()) {
    res.status(404).send({ error:'carrito '+req.params.carrito+' no existente' });
  }
  else {
    // Al enviar JSON la cabecera "Content-Type" se establece a "application/json; charset=utf-8":
    res.send(carrito.value());
  }
});


app.get('/:carrito/productos/:item', (req, res) => {
  const carrito= db.get('carritos').find({ nombre:req.params.carrito });
  if (!carrito.value()) {
    res.status(404).send({ error:'carrito '+req.params.carrito+' no existente' });
  }
  else {
    var item= carrito.get('productos').find({ item:req.params.item });
    if (!item.value()) {
      res.status(404).send({ error:'item '+req.params.item+' no existente' });
    }
    else {
      res.send({ result:item.value() });
    }
  }
});


app.delete('/:carrito/productos/:item', (req, res) => {
  const carrito= db.get('carritos').find({ nombre:req.params.carrito });
  if (!carrito.value()) {
    res.status(404).send({ error:'carrito '+req.params.carrito+' no existente' });
  }
  else {
    var item= carrito.get('productos').find({ item:req.params.item });
    if (!item.value()) {
      res.status(404).send({ error:'item '+req.params.item+' no existente' });
    }
    else {
      carrito.get('productos').remove({ item:req.params.item }).write();
      res.send({ result:'OK' });
    }
  }
});


app.delete('/:carrito', (req, res) => {
  const carrito= db.get('carritos').find({ nombre:req.params.carrito });
  if (!carrito.value()) {
    res.status(404).send({ error:'carrito '+req.params.carrito+' no existente' });
  }
  else {
    db.get('carritos').remove({ nombre:req.params.carrito }).write();
    res.send({ result:'OK' });
  }
});


const secret= '0000';

app.get('/clear', (req,res) => {
  if (req.query.secret===secret) {
    db.set('carritos',[]).write();
    res.send({ result:'OK' });
  }
  else {
    res.status(404).send();
  }
});


app.post('/carrito', (req,res) => {
  if (db.get('carritos').size().value()>= MAX_CARRITOS) {
    res.status(404).send({ error:'no caben más carritos; contacta con el administrador' });
  }
  let existe= true;
  while (existe) {
    var nuevo = Math.random().toString(36).substring(7);
    existe= db.get('carritos').find({ nombre:nuevo }).value();
  }
  db.get('carritos').push({ nombre:nuevo,productos:[] }).write();
  res.send({ result:{ nombre:nuevo } });
});


app.put('/:carrito/productos/:item', (req, res) => {
  const carrito= db.get('carritos').find({ nombre:req.params.carrito });
  if (!carrito.value()) {
    res.status(404).send({ error:'carrito '+req.params.carrito+' no existente' });
  }
  else {
    var item= carrito.get('productos').find({ item:req.params.item });
    if (!item.value()) {
      res.status(404).send({ error:'item '+req.params.item+' no existente' });
    }
    else {
      item.assign({ cantidad:req.body.cantidad }).write();
      res.send({ result:'OK' });
    }
  }
});


app.post('/:carrito/productos', (req, res) => {
  if (!req.body.item || !req.body.cantidad) {
    res.status(404).send({ error:'datos mal formados' })  
  }
  const carrito= db.get('carritos').find({ nombre:req.params.carrito });
  if (carrito.value()) {
    const item= carrito.get('productos').find({ item:req.body.item });
    if (!item.value()) {
      if (carrito.get('productos').size().value()>= MAX_PRODUCTOS) {
        res.status(404).send({ error:'no caben más productos en el carrito '+req.params.carrito });
      }
      carrito.get('productos').push({ item:req.body.item,cantidad:req.body.cantidad }).write();
      res.send({ result:'OK' });
    }
    else {
      res.status(404).send({ error:'item '+req.body.item+' ya existente' })
    } 
  }
  else {
    res.status(404).send({ error:'carrito '+req.params.carrito+' no existente' })
  }
});


const path = require('path');
const public = path.join(__dirname, 'public');
// __dirname: carpeta del proyecto

app.get('/', (req, res) => {
  res.send('API web para gestionar carritos de la compra');
});

app.get('/ayuda', (req, res) => res.sendFile(path.join(public, 'instrucciones.html'))
);

app.use('/', express.static(public));

app.listen(PORT, function () {
  console.log(`Aplicación lanzada en el puerto ${ PORT }!`);
});


// sudo snap install --classic heroku

// Para ejecutar en local:
// $ npm install  [si existe package.json]
// $ npm install express lowdb
// $ node index.js

// $ heroku local web  [o npm start]

// Para desplegar en Heroku:
// $ git add .
// $ git commit -m "Descripción de cambios"
// $ heroku login
// $ heroku create
// $ git push heroku master
// $ heroku open
// $ heroku ps
