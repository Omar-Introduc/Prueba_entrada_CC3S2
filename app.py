from flask import Flask, request, jsonify

app = Flask(__name__)

# Simulated in-memory database
libros = [
    {
        "id": 1,
        "titulo": "Cien años de soledad",
        "autor": "Gabriel García Márquez",
        "año_publicacion": 1967,
        "genero": "Realismo Mágico"
    },
    {
        "id": 2,
        "titulo": "1984",
        "autor": "George Orwell",
        "año_publicacion": 1949,
        "genero": "Distopía"
    }
]
siguiente_id = 3

# Get all books
@app.route('/libros', methods=['GET'])
def get_libros():
    return jsonify(libros)

# Get a specific book
@app.route('/libros/<int:libro_id>', methods=['GET'])
def get_libro(libro_id):
    libro = next((libro for libro in libros if libro['id'] == libro_id), None)
    if libro:
        return jsonify(libro)
    return jsonify({"mensaje": "Libro no encontrado"}), 404

# Add a new book
@app.route('/libros', methods=['POST'])
def add_libro():
    global siguiente_id
    nuevo_libro = {
        'id': siguiente_id,
        'titulo': request.json['titulo'],
        'autor': request.json['autor'],
        'año_publicacion': request.json['año_publicacion'],
        'genero': request.json['genero']
    }
    libros.append(nuevo_libro)
    siguiente_id += 1
    return jsonify(nuevo_libro), 201

# Update a book
@app.route('/libros/<int:libro_id>', methods=['PUT'])
def update_libro(libro_id):
    libro = next((libro for libro in libros if libro['id'] == libro_id), None)
    if not libro:
        return jsonify({"mensaje": "Libro no encontrado"}), 404

    libro['titulo'] = request.json.get('titulo', libro['titulo'])
    libro['autor'] = request.json.get('autor', libro['autor'])
    libro['año_publicacion'] = request.json.get('año_publicacion', libro['año_publicacion'])
    libro['genero'] = request.json.get('genero', libro['genero'])

    return jsonify(libro)

# Delete a book
@app.route('/libros/<int:libro_id>', methods=['DELETE'])
def delete_libro(libro_id):
    global libros
    libro = next((libro for libro in libros if libro['id'] == libro_id), None)
    if not libro:
        return jsonify({"mensaje": "Libro no encontrado"}), 404

    libros = [libro for libro in libros if libro['id'] != libro_id]
    return '', 204

if __name__ == '__main__':
    app.run(debug=True)
