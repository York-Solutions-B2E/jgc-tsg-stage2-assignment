#!/usr/bin/env bash

function get-script-dir {
    # Resolve possible symlinks to script file
    src=$0
    while [ -h "$src" ]; do
        dir=$(cd -P "$(dirname "$src")" && pwd)
        src=$(readlink "$src")
        # If it's not an absolute path, then append it
        if [[ $src != /* ]]; then
            src=$dir/$src
        fi
    done

    # Get the full traversal required
    dir=$(cd -P "$(dirname "$src")" && pwd)
    echo "$dir"
}

# Get the directory
scriptdir=$(get-script-dir)
tsgfrontenddir="$scriptdir/front-end"

cd $tsgfrontenddir
if [ ! -d node_modules ]; then
    npm i
fi
npm run build && npm run preview
