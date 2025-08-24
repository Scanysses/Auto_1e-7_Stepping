<h1>Auto 1e-7 Stepping
</h1>
<p><b>Little utility mod to make 1e-7 stepping mechanics easier to use</b><br><br></p>

<details>
<summary><b>If you don't know what 1e-7 stepping is, here's quick (and not the most accurate) explanation to understand the basic principals of this mechanic:</b></summary>

<table>
  <tr>
    <td style="width: 75%">
      <p>
         If you move along any axis less than the hardcoded value of 1e-7 (or 0.0000001) — the movement gets canceled. This brings us to the collision checks. When you're about to collide with a block — Minecraft doesn't let you do it and sets your position next to the edge of a block so the distance to it is about 1e-8. Then the game squares the movement vector and if the result is less than 1e-7 — movement gets canceled.
        <br>
        But if you're about to step (for example, from the slab, or to the slab, or otherwise move up half a block or less), the checks will run like that:<br>
        First check — sees your movement is greater than 1e-7 -> sees you're about to collide with the block -> sees that you need to make a step -> sets your position to 1e-8 from the block and makes a step (moves you half a block higher).
        <br>
          Second check — sees that your movement vector squared is greater than 1e-7 (since you're also moving upwards) and doesn't do anything.
        <br>
        Then the game checks the collision with the top block. If there's nothing — the game lets you move further. If there's a block, it again sets your position next to the edge, but still thinks that you're standing on the block that gives you a little time gap to make a jump.
       </p>
       <p><a href="https://docs.google.com/presentation/d/1gcTlhy8Je6xVHNQDKYxLF217fsE0rtVu3wbLu6bD8gw">Here you can find more detailed explanation of how this mechanics works (*click*).</a></p>
     </td>
     <td>
       <img src="https://cdn.modrinth.com/data/cached_images/b71ab478afa26adb914d4d70e1a508fc4b44ca94.png" width="400" alt="minecraft movement explanation picture 1">
       <img src="https://cdn.modrinth.com/data/cached_images/0057f6d58d098aa0695dc5325a856766a0753696.png" alt="minecraft movement explanation picture 2">
     </td>
   </tr>
</table>

</details>

<H2>ATTENTION</H2>
<p>This mod requires <a href="https://modrinth.com/mod/fabric-api">Fabric API</a> and <a href="https://modrinth.com/mod/yacl">Yet Another Config Lib</a> to work correctly!</p>

<H2>HOW TO USE</H2>
<p>
  To position yourself you have to hit a wall first.
</p>
<img src="https://cdn.modrinth.com/data/cached_images/64695c6b11e6fb51f286b6dc42c74a42e0f0d60a.gif" width="700" alt="hit the wall demonstration">

<p>
  Then press positioning key
</p>

```
By default keybind is set to "O"
```

<p>
  Or run the command 
</p>

```
/auto1e7 positioning
```


<table>
  <tr>
    <td style="width: 50%">
      <img src="https://cdn.modrinth.com/data/cached_images/e039c6f20a7868e991c7b86b9ee8bb0687baeb7f.gif" alt="First positioning mode demonstration">
    </td>
    <td>
      <img src="https://cdn.modrinth.com/data/cached_images/e3085cf38320c9f42e1568212eaccb3594f5f56d.gif" alt="Second positioning mode demonstration">
    </td>
  </tr>
  <tr>
    <td>
      First positioning mode
    </td>
    <td>
      Second positioning mode
    </td>
  </tr>
</table>

<p>
  You have two positioning modes:<br><br>
  - Packet (sends packet on server with exact position you need to be moved)<br>
  - Script (performs series of actions that player can perform manually, but does it a bit faster)<br><br>
  You can switch between positioning modes by opening the config screen in modmenu tab or by using the command
</p>

```
/auto1e7 config
```

<img src="https://cdn.modrinth.com/data/cached_images/5ad242c0ec2647a4f4199bef28e4568b2bd7d227.gif" width="700" alt="Config UI opening command demonstration">

<br>

<h2>
  Languages supported<br>
</h2>
<p>
  - English<br>
  - Russian (may be not 100% accurate)<br>
  - Simplified Chinese (may be not 100% accurate)
</p>
<p><b>
  If you want to help with mod translation — you can contact me on discord <a href="https://discord.com/channels/@me">scany_alt</a>
</b></p>
<br>
<p><b>
  If you found a bug — please report it on <a href="https://github.com/Scanysses/Auto_1e-7_Stepping/issues">GitHub's Issues page</a>
</b></p>
<br>