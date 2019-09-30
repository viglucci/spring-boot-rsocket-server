const argv = require('yargs').argv
const shell = require("shelljs");
const path = require("path");

const arguments = argv._;
const fileName = arguments[0];

const PROTOC_GEN_PLUGIN_PATH = path.resolve("./node_modules/.bin/rsocket_rpc_js_protoc_plugin.cmd");
const OUT_DIR = path.resolve("./src/generated/rsocket");
const PROTO_PATH = path.resolve(`../src/main/proto/`);
const RSOCKET_PROTO_PATH = path.resolve("node_modules/rsocket-rpc-protobuf/proto");
const INPUT_PATH = path.resolve(`../src/main/proto/${fileName}.proto`);

let cmd = [
    `protoc`,
    `--proto_path=${PROTO_PATH}`,
    `--proto_path=${RSOCKET_PROTO_PATH}`,
    `--rsocket_rpc_out=${OUT_DIR}`,
    `--js_out="import_style=commonjs,binary:${OUT_DIR}"`,
    `--plugin="protoc-gen-rsocket_rpc=${PROTOC_GEN_PLUGIN_PATH}"`,
    `${INPUT_PATH}`
];

cmd = cmd.join(" ");

console.log(cmd);

// Run external tool synchronously
if (shell.exec(cmd).code !== 0) {
    shell.echo('Error: command failed');
    shell.exit(1);
} else {
    shell.echo('Command finished');
    shell.exit(0);
}
