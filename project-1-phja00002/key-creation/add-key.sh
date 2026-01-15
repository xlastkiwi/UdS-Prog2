
# check if key already exists
if [ -f ~/.ssh/id_ed25519 ]; then
    echo "Key already exists"
else
    ssh-keygen -t ed25519 -N "" -f ~/.ssh/id_ed25519
fi
apk add py3-requests
# check if server.py is running, if so, kill it
python server.py 1 &
python test.py
