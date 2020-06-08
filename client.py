import sys
sys.path.append("gen-py")
from Forwarding import Forwarding
from Forwarding.constants import Excep

from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.transport.TTransport import TTransportException
from thrift.protocol import TBinaryProtocol

client = None
def connect(port = 15000):
    try:
        global client
        trans = TSocket.TSocket("localhost", port)
        trans = TTransport.TBufferedTransport(trans)
        proto = TBinaryProtocol.TBinaryProtocol(trans)
        client = Forwarding.Client(proto)
        trans.open()
        return True
    except TTransportException as e:
        print(bcolors.FAIL + "En servidor es inalcanzable" + bcolors.ENDC)
        return False

def save(key, value):
    try:
        print(bcolors.OKGREEN + "La respuesta fue: " + str(client.save(key, value)) + bcolors.ENDC)
    except Excep as e:
        print(bcolors.FAIL + "id: " + str(e.id) + " - detalle: " + e.detail + bcolors.ENDC)

def get(key):
    try:
        print(bcolors.OKGREEN + "La respuesta fue: " + str(client.get(key)) + bcolors.ENDC)
    except Excep as e:
        print(bcolors.FAIL + "id: " + str(e.id) + " - detalle: " + e.detail + bcolors.ENDC)

def delete(key):
    try:
        print(bcolors.OKGREEN + "La respuesta fue: " + str(client.destroy(key)) + bcolors.ENDC)
    except Excep as e:
        print(bcolors.FAIL + "id: " + str(e.id) + " - detalle: " + e.detail + bcolors.ENDC)

class bcolors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'

print(bcolors.HEADER + "Bienvenido al cliente magico" + bcolors.ENDC)
isConnected = False
while not isConnected:
    print("a que puerto desea conectarse?")
    port = input()
    isConnected = connect(port)
while True:
    print("1. Guardar clave-valor")
    print("2. Recuperar valor por clave")
    print("3. Eliminar valor por clave")
    print("4. Salir")
    option = input()
    if int(option) == 1:
        print("Key: ")
        key = input()
        print("value: ")
        value = input()
        save(key, value)
    elif int(option) == 2:
        print("Key: ")
        key = input()
        get(key)
    elif int(option) == 3:
        print("Key: ")
        key = input()
        delete(key)
    elif int(option) == 4:
        print(bcolors.OKBLUE + "Hasta la proxima!" + bcolors.ENDC)
        exit()