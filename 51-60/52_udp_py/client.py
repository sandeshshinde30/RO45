import socket

HOST = '127.0.0.1'
PORT = 8080

with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
    s.sendto(b"Hello from UDP client", (HOST, PORT))
    print("Message sent")
    data, addr = s.recvfrom(1024)
    print(f"Received from {addr}: {data.decode()}")