set - e

# compilar proyecto y genera los JARS con sus depencias
docker run -it --rm -v "$(pwd)":/workspaces/laboratorio-2 \
    -w /workspaces/laboratorio-2 maven:3.6.3-openjdk-15 sh build.sh