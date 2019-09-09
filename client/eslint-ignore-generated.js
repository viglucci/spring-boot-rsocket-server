const fs = require("fs-jetpack");
const path = require("path");
const { EOL } = require("os");

const OUT_DIR = path.resolve("./src/generated/rsocket");

const files = fs.list(OUT_DIR);

files.forEach((fileName) => {
	const filePath = path.resolve(OUT_DIR, fileName);
	const header = "/* eslint-disable */";
	console.log(`Appending ${header} to ${filePath}`);
	const contents = fs.read(filePath);
	fs.write(filePath, `${header}${EOL}${contents}`);
});