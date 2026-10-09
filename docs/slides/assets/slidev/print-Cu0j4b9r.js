import{C as e,L as t,b as n,g as r,h as i,ht as a,p as o,v as s,x as c,y as l,yt as u,z as d}from"../modules/shiki-BxKw0iCq.js";import{u as f}from"./utils-D1mXKVOr.js";import{S as p}from"../modules/vue-DtRkqVSc.js";import{y as m,yt as h}from"../index-C5ys70Dx.js";import{t as g}from"./NoteDisplay-6JjsWBeC.js";var _={id:`page-root`},v={class:`m-4`},y={class:`mb-10`},b={class:`text-4xl font-bold mt-2`},x={class:`opacity-50`},S={class:`text-lg`},C={class:`font-bold flex gap-2`},w={class:`opacity-50`},T={key:0,class:`border-main mb-8`},E=e({__name:`print`,setup(e){let{slides:E,total:D}=m();p(`
@page {
  size: A4;
  margin-top: 1.5cm;
  margin-bottom: 1cm;
}
* {
  -webkit-print-color-adjust: exact;
}
html,
html body,
html #app,
html #page-root {
  height: auto;
  overflow: auto !important;
}
`),h({title:`Notes - ${f.title}`});let O=i(()=>E.value.map(e=>e.meta?.slide).filter(e=>e!==void 0&&e.noteHTML!==``));return(e,i)=>(t(),l(`div`,_,[r(`div`,v,[r(`div`,y,[r(`h1`,b,u(a(f).title),1),r(`div`,x,u(new Date().toLocaleString()),1)]),(t(!0),l(o,null,d(O.value,(e,o)=>(t(),l(`div`,{key:o,class:`flex flex-col gap-4 break-inside-avoid-page`},[r(`div`,null,[r(`h2`,S,[r(`div`,C,[r(`div`,w,u(e?.no)+`/`+u(a(D)),1),n(` `+u(e?.title)+` `,1),i[0]||=r(`div`,{class:`flex-auto`},null,-1)])]),c(g,{"note-html":e.noteHTML,class:`max-w-full`},null,8,[`note-html`])]),o<O.value.length-1?(t(),l(`hr`,T)):s(`v-if`,!0)]))),128))])]))}});export{E as default};