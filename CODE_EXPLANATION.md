# Explanation of the Network Infrastructure Code

This Python code models a simple network infrastructure using object-oriented programming. It defines classes for network devices like routers and switches, as well as the connections between them.

## Classes

### `Puerto` (Port)

Represents a physical port on a network device.

- **Attributes:**
    - `numero`: The port number.
    - `velocidad`: The speed of the port (e.g., "1Gbps").
    - `estado`: The status of the port ("conectado", "desconectado", "error").
    - `conexion`: A reference to the connection object it's part of.

- **Methods:**
    - `conectar(otra_conexion)`: Connects the port to a connection.
    - `desconectar()`: Disconnects the port.
    - `__str__()`: Returns a string representation of the port.

### `DispositivoDeRed` (Network Device)

A base class for network devices.

- **Attributes:**
    - `nombre`: The name of the device.
    - `ip_address`: The IP address of the device.
    - `num_puertos`: The number of ports on the device.
    - `puertos`: A dictionary of `Puerto` objects.
    - `estado`: The status of the device ("encendido", "apagado").
    - `id`: A unique ID for the device.

- **Methods:**
    - `encender()`: Turns the device on.
    - `apagar()`: Turns the device off and disconnects all ports.
    - `obtener_puerto(numero_puerto)`: Returns a specific port object.
    - `mostrar_info()`: Prints information about the device.

### `Router`

A subclass of `DispositivoDeRed` that represents a router.

- **Attributes (in addition to `DispositivoDeRed`):**
    - `protocolos_enrutamiento`: A list of routing protocols the router supports.
    - `tabla_enrutamiento`: The router's routing table.

- **Methods:**
    - `configurar_ruta(destino, siguiente_salto)`: Adds a route to the routing table.
    - `mostrar_info()`: Prints router-specific information.

### `Switch`

A subclass of `DispositivoDeRed` that represents a switch.

- **Attributes (in addition to `DispositivoDeRed`):**
    - `capacidad_mac`: The maximum number of MAC addresses the switch can store.
    - `tabla_mac`: The switch's MAC address table.

- **Methods:**
    - `aprender_mac(mac_address, puerto)`: Adds a MAC address to the MAC address table.
    - `mostrar_info()`: Prints switch-specific information.

### `Conexion` (Connection)

Represents a connection between two ports.

- **Attributes:**
    - `id`: A unique ID for the connection.
    - `puerto1`: The first port in the connection.
    - `puerto2`: The second port in the connection.
    - `estado`: The status of the connection ("activa", "inactiva", "error").

- **Methods:**
    - `establecer_conexion()`: Establishes the connection between the two ports.
    - `desconectar()`: Disconnects the connection.
    - `__str__()`: Returns a string representation of the connection.

## Network Simulation

The last part of the code simulates a small network:

1.  **Device Creation:** It creates two routers (`Router_Principal`, `Router_Sucursal`) and one switch (`Switch_Oficina`).
2.  **Initial Information:** It displays the initial information of the created devices.
3.  **Establishing Connections:** It establishes connections between `Router_Principal` and `Switch_Oficina`, and between `Router_Principal` and `Router_Sucursal`.
4.  **Simulating Network Activity:**
    -   The switch learns two MAC addresses on different ports.
    -   The main router configures a route to the branch office network.
5.  **Verifying States:** It displays the updated information of the devices.
6.  **Testing Power Off:** It turns off `Router_Principal` and shows how the connected ports are disconnected.
7.  **Testing Power On:** It turns `Router_Principal` back on.
8.  **Disconnecting a Connection:** It disconnects the connection between `Router_Principal` and `Switch_Oficina`.
