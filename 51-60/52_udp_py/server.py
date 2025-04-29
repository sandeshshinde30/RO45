import socket

HOST = '127.0.0.1'
PORT = 8080

with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
    s.bind((HOST, PORT))
    print(f"UDP Server listening on {HOST}:{PORT}")
    data, addr = s.recvfrom(1024)
    print(f"Received from {addr}: {data.decode()}")
    s.sendto(b"Hello from UDP server", addr)