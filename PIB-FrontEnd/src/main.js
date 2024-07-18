import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import * as monaco from "monaco-editor";

const app = createApp(App);
app.use(router);
app.mount("#app");

self.MonacoEnvironment = {
  getWorkerUrl: function (moduleId, label) {
    if (label === "json") {
      return "/json.worker.js";
    }
    if (label === "css" || label === "scss" || label === "less") {
      return "/css.worker.js";
    }
    if (label === "html" || label === "handlebars" || label === "razor") {
      return "/html.worker.js";
    }
    if (label === "typescript" || label === "javascript") {
      return "/ts.worker.js";
    }
    return "/editor.worker.js";
  },
};
