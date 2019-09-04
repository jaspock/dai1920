function logResult(result) {
  console.log(result);
}

function logError(error) {
  console.log('Looks like there was a problem: \n', error);
}

function validateResponse(response) {
  if (!response.ok) {
    throw Error(response.statusText);
  }
  return response;
}

function readResponseAsJSON(response) {
  return response.json();
}

function fetchJSON(pathToResource,accion,datos) {
  var init= {method:accion};
  if (datos) {
    init.body= JSON.stringify(datos);
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Accept", "application/json");
    init.headers= myHeaders;
  }

  fetch(pathToResource, init)
  .then(validateResponse)
  .then(readResponseAsJSON)
  .then(logResult)
  .catch(logError);
}

fetchJSON('api/productos','GET');
fetchJSON('api/productos/galletas','GET');
fetchJSON('api/productos/patatas','GET');
fetchJSON('api/productos/galletas','DELETE');
fetchJSON('api/productos/','GET');
fetchJSON('api/productos/','POST',{nombre:"patatas", unidades:"1000"});
fetchJSON('api/productos/','GET');
fetchJSON('api/productos/','PUT',{nombre:"patatas", unidades:"10"});
fetchJSON('api/productos/','GET');
