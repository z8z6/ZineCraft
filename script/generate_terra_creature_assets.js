const fs = require('fs');
const path = require('path');
const zlib = require('zlib');
const crypto = require('crypto');

const root = path.resolve(__dirname, '..');
const referenceRoot = process.argv[2] || 'E:\\project\\asset\\生物';
const textureDir = path.join(root, 'src/main/resources/assets/zinecraft/textures/entity');
const modelDir = path.join(root, 'src/main/resources/assets/zinecraft/blockbench/entity');
fs.mkdirSync(textureDir, { recursive: true });
fs.mkdirSync(modelDir, { recursive: true });

function crc32(buffer) {
  let crc = 0xffffffff;
  for (const byte of buffer) {
    crc ^= byte;
    for (let bit = 0; bit < 8; bit++) crc = (crc >>> 1) ^ ((crc & 1) ? 0xedb88320 : 0);
  }
  return (crc ^ 0xffffffff) >>> 0;
}

function chunk(type, data) {
  const name = Buffer.from(type);
  const length = Buffer.alloc(4);
  length.writeUInt32BE(data.length);
  const checksum = Buffer.alloc(4);
  checksum.writeUInt32BE(crc32(Buffer.concat([name, data])));
  return Buffer.concat([length, name, data, checksum]);
}

function png(width, height, pixel) {
  const raw = Buffer.alloc((width * 4 + 1) * height);
  for (let y = 0; y < height; y++) {
    const row = y * (width * 4 + 1);
    raw[row] = 0;
    for (let x = 0; x < width; x++) {
      const [r, g, b, a = 255] = pixel(x, y);
      const offset = row + 1 + x * 4;
      raw[offset] = r;
      raw[offset + 1] = g;
      raw[offset + 2] = b;
      raw[offset + 3] = a;
    }
  }
  const signature = Buffer.from([137, 80, 78, 71, 13, 10, 26, 10]);
  const ihdr = Buffer.alloc(13);
  ihdr.writeUInt32BE(width, 0);
  ihdr.writeUInt32BE(height, 4);
  ihdr[8] = 8;
  ihdr[9] = 6;
  return Buffer.concat([signature, chunk('IHDR', ihdr), chunk('IDAT', zlib.deflateSync(raw)), chunk('IEND', Buffer.alloc(0))]);
}

function mix(a, b, amount) {
  return a.map((value, index) => Math.round(value + (b[index] - value) * amount));
}

const palettes = {
  rivenbeast: { base: [83, 73, 69], light: [151, 141, 132], dark: [38, 36, 38], accent: [214, 206, 181] },
  clampbeast: { base: [91, 77, 54], light: [147, 126, 73], dark: [38, 34, 29], accent: [193, 103, 42] },
  packbeast: { base: [112, 91, 76], light: [173, 151, 126], dark: [39, 34, 34], accent: [221, 209, 178] }
};

function textureFor(id) {
  const p = palettes[id];
  return png(128, 128, (x, y) => {
    const hash = ((x * 37 + y * 61 + ((x * y) % 17) * 13) >>> 0) % 100;
    let color = mix(p.base, p.light, hash < 18 ? 0.28 : hash < 55 ? 0.10 : 0);
    if (((x + y * 3) % 29) < 3) color = mix(color, p.dark, 0.24);
    if (x >= 96 && y < 32) color = p.dark;
    if (x >= 96 && y >= 32 && y < 64) color = p.accent;
    if (id === 'rivenbeast' && y > 72 && ((x + y) % 13 < 5)) color = mix(p.light, [226, 224, 211], 0.45);
    if (id === 'clampbeast' && y > 70 && ((x * 2 + y) % 19 < 4)) color = mix(p.base, p.accent, 0.38);
    if (id === 'packbeast' && x > 48 && x < 92 && y > 68) color = mix(p.light, p.accent, 0.36);
    return [...color, 255];
  });
}

for (const id of Object.keys(palettes)) fs.writeFileSync(path.join(textureDir, `${id}.png`), textureFor(id));

const sandSource = path.join(referenceRoot, '沙地兽', 'armored_beast.bbmodel');
if (!fs.existsSync(sandSource)) throw new Error(`Missing reference Blockbench model: ${sandSource}`);
const sandModel = JSON.parse(fs.readFileSync(sandSource, 'utf8'));
const sandTexture = sandModel.textures?.[0]?.source;
if (!sandTexture?.startsWith('data:image/png;base64,')) throw new Error('Sandbeast bbmodel has no embedded PNG texture');
fs.writeFileSync(path.join(textureDir, 'sandbeast.png'), Buffer.from(sandTexture.substring(sandTexture.indexOf(',') + 1), 'base64'));
fs.copyFileSync(sandSource, path.join(modelDir, 'sandbeast.bbmodel'));

function uuid(id, name) {
  const hash = crypto.createHash('md5').update(`${id}:${name}`).digest('hex');
  return `${hash.slice(0, 8)}-${hash.slice(8, 12)}-4${hash.slice(13, 16)}-a${hash.slice(17, 20)}-${hash.slice(20, 32)}`;
}

function cube(id, bone, name, from, to, uv = [0, 0], rotation = [0, 0, 0], origin = [0, 0, 0]) {
  return { name, box_uv: true, rescale: false, locked: false, from, to, autouv: 0, color: 0,
    rotation, origin, uv_offset: uv, type: 'cube', uuid: uuid(id, `${bone}:${name}`), bone };
}

function commonQuadruped(id, kind) {
  const e = [];
  const add = (bone, name, from, to, uv, rotation, origin) => e.push(cube(id, bone, name, from, to, uv, rotation, origin));
  if (kind === 'riven') {
    add('body', 'torso', [-7, 8, -8], [7, 18, 9], [0, 0]);
    add('body', 'shoulder_fur', [-8, 7, -9], [8, 19, -2], [48, 40]);
    add('body', 'back_mane', [-6, 18, -7], [6, 21, 7], [64, 48]);
    add('head', 'head', [-5, 7, -17], [5, 15, -9], [0, 32]);
    add('head', 'muzzle', [-3.5, 6, -21], [3.5, 11, -16], [40, 32]);
    add('head', 'left_ear', [2.5, 14, -14], [5.5, 18, -11], [96, 0], [0, 0, -18], [3.5, 15, -13]);
    add('head', 'right_ear', [-5.5, 14, -14], [-2.5, 18, -11], [96, 0], [0, 0, 18], [-3.5, 15, -13]);
    add('jaw', 'fang_bar', [-3, 4.5, -20.5], [3, 7, -16], [96, 32]);
  } else if (kind === 'pack') {
    add('body', 'torso', [-8, 8, -9], [8, 19, 10], [0, 0]);
    add('body', 'hump', [-7, 16, -5], [7, 24, 7], [48, 40]);
    add('body', 'shoulder_plate', [-8.5, 14, -10], [8.5, 21, -4], [64, 48]);
    add('head', 'head', [-5.5, 8, -18], [5.5, 16, -9], [0, 32]);
    add('head', 'muzzle', [-4.5, 5, -23], [4.5, 11, -16], [40, 32]);
    add('head', 'nose_horn', [-1.5, 10, -27], [1.5, 14, -21], [96, 32], [-30, 0, 0], [0, 11, -22]);
    add('head', 'left_ear', [3.5, 14, -14], [6, 18, -11], [96, 0]);
    add('head', 'right_ear', [-6, 14, -14], [-3.5, 18, -11], [96, 0]);
    add('jaw', 'lower_muzzle', [-3.5, 4.5, -21], [3.5, 7, -16], [96, 32]);
  }
  add('tail', 'tail_base', [-2, 10, 8], [2, 14, 15], [72, 0], [30, 0, 0], [0, 12, 9]);
  add('tail', 'tail_tip', [-1.2, 8, 14], [1.2, 11, 20], [84, 0], [40, 0, 0], [0, 10, 15]);
  const frontZ = kind === 'pack' ? -8 : -7;
  const backZ = kind === 'pack' ? 8 : 7;
  for (const [bone, x, z] of [['front_left', 5, frontZ], ['front_right', -5, frontZ], ['back_left', 5, backZ], ['back_right', -5, backZ]]) {
    add(bone, `${bone}_upper`, [x - 1.8, 14, z - 2], [x + 1.8, 21, z + 2], [64, 0]);
    add(bone, `${bone}_lower`, [x - 1.4, 20, z - 1.5], [x + 1.4, 24, z + 1.5], [80, 0]);
  }
  return e;
}

function clampGeometry(id) {
  const e = [];
  const add = (bone, name, from, to, uv, rotation, origin) => e.push(cube(id, bone, name, from, to, uv, rotation, origin));
  add('body', 'carapace', [-7, 12, -7], [7, 19, 8], [0, 0]);
  add('body', 'upper_shell', [-6, 17, -5], [6, 22, 7], [48, 40]);
  add('head', 'wedge_head', [-6, 12, -13], [6, 19, -6], [0, 32]);
  add('jaw', 'mouth_parts', [-3, 10, -16], [3, 14, -11], [96, 32]);
  add('front_left', 'left_pincer_arm', [4, 12, -15], [8, 17, -9], [64, 0], [0, -18, -12], [5, 14, -10]);
  add('front_left', 'left_pincer', [5, 10, -20], [10, 16, -14], [96, 32], [0, -12, -18], [6, 13, -15]);
  add('front_right', 'right_pincer_arm', [-8, 12, -15], [-4, 17, -9], [64, 0], [0, 18, 12], [-5, 14, -10]);
  add('front_right', 'right_pincer', [-10, 10, -20], [-5, 16, -14], [96, 32], [0, 12, 18], [-6, 13, -15]);
  for (const [bone, x, z, roll] of [
    ['middle_left', 6, -2, -28], ['middle_right', -6, -2, 28],
    ['back_left', 6, 5, -32], ['back_right', -6, 5, 32]
  ]) {
    const outerX = x + 5 * Math.sign(x);
    add(bone, `${bone}_thigh`, [Math.min(x - 1.5, outerX), 15, z - 2], [Math.max(x + 1.5, outerX), 18, z + 2], [64, 0], [0, 0, roll], [x, 16, z]);
    add(bone, `${bone}_shin`, [outerX - 1.4, 16, z - 1.4], [outerX + 1.4, 24, z + 1.4], [80, 0], [0, 0, -roll / 2], [outerX, 17, z]);
  }
  add('tail', 'abdomen_tip', [-4, 13, 7], [4, 18, 12], [72, 0]);
  return e;
}

let keyframeSerial = 0;

function keyframe(channel, time, vector, interpolation = 'linear') {
  return { channel, data_points: [{ x: vector[0], y: vector[1], z: vector[2] }], uuid: uuid('keyframe', String(keyframeSerial++)), time, color: -1, interpolation };
}

function animation(id, name, length, loop, bones) {
  const animators = {};
  for (const [bone, frames] of Object.entries(bones)) {
    const boneId = uuid(id, `group:${bone}`);
    animators[boneId] = { name: bone, type: 'bone', keyframes: frames.flatMap(([time, rotation, position]) => {
      const result = [keyframe('rotation', time, rotation)];
      if (position) result.push(keyframe('position', time, position));
      return result;
    }) };
  }
  return { uuid: uuid(id, `animation:${name}`), name: `animation.${id}.${name}`, loop, override: false,
    length, snapping: 20, selected: false, saved: true, anim_time_update: '', blend_weight: '', start_delay: '', loop_delay: '', animators };
}

function writeModel(id, displayName, elements) {
  const boneNames = ['root', 'body', 'head', 'jaw', 'tail', 'front_left', 'front_right', 'middle_left', 'middle_right', 'back_left', 'back_right'];
  const groups = {};
  for (const bone of boneNames) groups[bone] = { name: bone, origin: [0, 12, 0], color: 0, uuid: uuid(id, `group:${bone}`), export: true,
    isOpen: true, locked: false, visibility: true, autouv: 0, children: elements.filter(e => e.bone === bone).map(e => e.uuid) };
  groups.root.children = boneNames.filter(name => name !== 'root').map(name => groups[name]);
  const texture = fs.readFileSync(path.join(textureDir, `${id}.png`));
  const walkFrames = {
    front_left: [[0, [28, 0, 0]], [0.5, [-28, 0, 0]], [1, [28, 0, 0]]],
    back_right: [[0, [28, 0, 0]], [0.5, [-28, 0, 0]], [1, [28, 0, 0]]],
    front_right: [[0, [-28, 0, 0]], [0.5, [28, 0, 0]], [1, [-28, 0, 0]]],
    back_left: [[0, [-28, 0, 0]], [0.5, [28, 0, 0]], [1, [-28, 0, 0]]],
    middle_left: [[0, [20, 0, 0]], [0.5, [-20, 0, 0]], [1, [20, 0, 0]]],
    middle_right: [[0, [-20, 0, 0]], [0.5, [20, 0, 0]], [1, [-20, 0, 0]]]
  };
  const model = {
    meta: { format_version: '5.0', model_format: 'free', box_uv: true }, name: id,
    model_identifier: `com.cxxcxx.zinecraft.core.client.model.${displayName}Model`, visible_box: [2, 2, 0],
    variable_placeholders: '', variable_placeholder_buttons: [], timeline_setups: [], unhandled_root_fields: {},
    resolution: { width: 128, height: 128 }, elements: elements.map(({bone, ...entry}) => entry), outliner: [groups.root],
    textures: [{ path: '', name: `${id}.png`, folder: 'entity', namespace: 'zinecraft', id: '0', group: 'default',
      width: 128, height: 128, uv_width: 128, uv_height: 128, particle: false, use_as_default: false, layers_enabled: false,
      sync_to_project: '', render_mode: 'default', render_sides: 'auto', frame_time: 1, frame_order_type: 'loop', frame_order: '',
      frame_interpolate: false, visible: true, internal: true, saved: true, uuid: uuid(id, 'texture'),
      source: `data:image/png;base64,${texture.toString('base64')}` }],
    animations: [
      animation(id, 'idle', 2, 'loop', { body: [[0, [0, 0, 0]], [1, [1.5, 0, 0], [0, -0.4, 0]], [2, [0, 0, 0]]], head: [[0, [0, -3, 0]], [1, [2, 3, 0]], [2, [0, -3, 0]]] }),
      animation(id, 'walk', 1, 'loop', walkFrames),
      animation(id, 'attack', 0.65, 'once', { body: [[0, [0, 0, 0]], [0.25, [8, 0, 0]], [0.65, [0, 0, 0]]], head: [[0, [0, 0, 0]], [0.25, [-25, 0, 0]], [0.65, [0, 0, 0]]], jaw: [[0, [0, 0, 0]], [0.25, [24, 0, 0]], [0.65, [0, 0, 0]]] }),
      animation(id, 'hurt', 0.45, 'once', { body: [[0, [0, 0, 0]], [0.15, [-8, 0, 7]], [0.45, [0, 0, 0]]], head: [[0, [0, 0, 0]], [0.15, [12, 0, -7]], [0.45, [0, 0, 0]]] })
    ]
  };
  fs.writeFileSync(path.join(modelDir, `${id}.bbmodel`), JSON.stringify(model, null, 2) + '\n');
}

writeModel('rivenbeast', 'Rivenbeast', commonQuadruped('rivenbeast', 'riven'));
writeModel('clampbeast', 'Clampbeast', clampGeometry('clampbeast'));
writeModel('packbeast', 'Packbeast', commonQuadruped('packbeast', 'pack'));

console.log(`Generated Terra creature textures in ${textureDir}`);
console.log(`Generated Blockbench sources in ${modelDir}`);
