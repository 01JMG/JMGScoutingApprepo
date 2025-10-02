// server.js
const express = require('express');
const fs = require('fs');
const path = require('path');
const multer = require('multer');
const app = express();
const PORT = 3000;

app.use(express.json());

// -------------------------
// GLOBAL ERROR HANDLERS
// -------------------------
process.on('uncaughtException', (err) => {
    console.error('Uncaught Exception:', err);
});

process.on('unhandledRejection', (reason, promise) => {
    console.error('Unhandled Rejection at:', promise, 'reason:', reason);
});

// -------------------------
// LOG REQUESTS
// -------------------------
app.use((req, res, next) => {
    console.log(`${req.method} ${req.url}`);
    next();
});

// -------------------------
// MEDIA UPLOAD SETUP
// -------------------------
const uploadDir = path.join(__dirname, 'uploads');
if (!fs.existsSync(uploadDir)) fs.mkdirSync(uploadDir);

const storage = multer.diskStorage({
    destination: (req, file, cb) => cb(null, uploadDir),
    filename: (req, file, cb) => cb(null, Date.now() + '-' + file.originalname)
});
const upload = multer({ storage });
app.use('/uploads', express.static(uploadDir));

// -------------------------
// STORAGE FILES
// -------------------------
const POSTS_FILE = 'posts.txt';
const COMMENTS_FILE = 'comments.txt';
const REACTIONS_FILE = 'reactions.txt';
const SHARES_FILE = 'shares.txt';

// -------------------------
// UTILS
// -------------------------
const generateUniqueId = () => Date.now().toString(36) + Math.random().toString(36).substr(2);

function loadPosts() {
    if (!fs.existsSync(POSTS_FILE)) return [];
    return fs.readFileSync(POSTS_FILE, 'utf8')
        .split('\n')
        .filter(l => l.trim())
        .map(l => { try { return JSON.parse(l); } catch { return null; } })
        .filter(p => p);
}

// -------------------------
// POSTS
// -------------------------
app.post('/api/v1/on-the-field/posts', upload.single('media'), (req, res) => {
    const { agentId, content, tags } = req.body;

    if (!agentId || !content) return res.status(400).send('Agent ID and content are required.');

    const mediaPath = req.file ? `/uploads/${req.file.filename}` : null;

    const newPost = {
        id: generateUniqueId(),
        agentId,
        content,
        tags: tags ? tags.split(',') : [],
        mediaPath,
        createdAt: new Date().toISOString()
    };

    fs.appendFileSync(POSTS_FILE, JSON.stringify(newPost) + '\n');
    res.status(201).json(newPost);
});

app.get('/api/v1/on-the-field/posts', (req, res) => {
    const { agentId, tags } = req.query;
    let posts = loadPosts();
    if (agentId) posts = posts.filter(p => p.agentId === agentId);
    if (tags) {
        const filterTags = tags.split(',');
        posts = posts.filter(p => filterTags.every(t => p.tags.includes(t)));
    }
    res.json(posts);
});

app.get('/api/v1/on-the-field/posts/:postId', (req, res) => {
    const post = loadPosts().find(p => p.id === req.params.postId);
    if (!post) return res.status(404).send('Post not found');
    res.json(post);
});

// -------------------------
// COMMENTS
// -------------------------
app.post('/api/v1/on-the-field/posts/:postId/comments', (req, res) => {
    const { userId, commentText } = req.body;
    if (!userId || !commentText) return res.status(400).send('User ID and comment text are required.');

    const newComment = {
        id: generateUniqueId(),
        postId: req.params.postId,
        userId,
        commentText,
        createdAt: new Date().toISOString()
    };

    fs.appendFileSync(COMMENTS_FILE, JSON.stringify(newComment) + '\n');
    res.status(201).json(newComment);
});

app.get('/api/v1/on-the-field/posts/:postId/comments', (req, res) => {
    if (!fs.existsSync(COMMENTS_FILE)) return res.json([]);
    const comments = fs.readFileSync(COMMENTS_FILE, 'utf8')
        .split('\n')
        .filter(l => l.trim())
        .map(l => { try { return JSON.parse(l); } catch { return null; } })
        .filter(c => c && c.postId === req.params.postId);
    res.json(comments);
});

// -------------------------
// REACTIONS
// -------------------------
app.post('/api/v1/on-the-field/posts/:postId/reactions', (req, res) => {
    const { userId, reactionType } = req.body;
    if (!userId || !reactionType) return res.status(400).send('User ID and reaction type are required.');

    const newReaction = {
        id: generateUniqueId(),
        postId: req.params.postId,
        userId,
        reactionType,
        createdAt: new Date().toISOString()
    };

    fs.appendFileSync(REACTIONS_FILE, JSON.stringify(newReaction) + '\n');
    res.status(201).json(newReaction);
});

app.get('/api/v1/on-the-field/posts/:postId/reactions', (req, res) => {
    if (!fs.existsSync(REACTIONS_FILE)) return res.json({});
    const reactions = fs.readFileSync(REACTIONS_FILE, 'utf8')
        .split('\n')
        .filter(l => l.trim())
        .map(l => { try { return JSON.parse(l); } catch { return null; } })
        .filter(r => r && r.postId === req.params.postId)
        .reduce((acc, r) => {
            acc[r.reactionType] = (acc[r.reactionType] || 0) + 1;
            return acc;
        }, {});
    res.json(reactions);
});

// -------------------------
// PASSES / SHARES
// -------------------------
app.post('/api/v1/on-the-field/posts/:postId/pass', (req, res) => {
    const { userId, caption } = req.body;
    if (!userId) return res.status(400).send('User ID is required.');

    const posts = loadPosts();
    const originalPost = posts.find(p => p.id === req.params.postId);
    if (!originalPost) return res.status(404).send('Original post not found.');

    const newPass = {
        id: generateUniqueId(),
        postId: req.params.postId,
        userId,
        caption: caption || null,
        createdAt: new Date().toISOString()
    };

    fs.appendFileSync(SHARES_FILE, JSON.stringify(newPass) + '\n');

    res.status(201).json({ ...newPass, originalPost });
});

app.get('/api/v1/on-the-field/posts/:postId/shares', (req, res) => {
    if (!fs.existsSync(SHARES_FILE)) return res.json([]);
    const posts = loadPosts();
    const originalPost = posts.find(p => p.id === req.params.postId);

    const passes = fs.readFileSync(SHARES_FILE, 'utf8')
        .split('\n')
        .filter(l => l.trim())
        .map(l => { try { return JSON.parse(l); } catch { return null; } })
        .filter(s => s && s.postId === req.params.postId)
        .map(s => ({ ...s, originalPost }));

    res.json(passes);
});

app.get('/api/v1/on-the-field/posts/:postId/pass/count', (req, res) => {
    if (!fs.existsSync(SHARES_FILE)) return res.json({ count: 0 });
    const count = fs.readFileSync(SHARES_FILE, 'utf8')
        .split('\n')
        .filter(l => {
            if (!l.trim()) return false;
            try { return JSON.parse(l).postId === req.params.postId; } catch { return false; }
        }).length;
    res.json({ postId: req.params.postId, count });
});

// -------------------------
// START SERVER
// -------------------------
app.listen(PORT, () => {
    console.log(`OnTheFieldController listening on port ${PORT}`);
});
