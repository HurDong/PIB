<template>
  <div id="editor-container">
    <select v-model="selectedLanguage" @change="changeLanguage">
      <option v-for="lang in languages" :key="lang" :value="lang">{{ lang }}</option>
    </select>
    <div id="editor" style="height: 90vh;"></div>
  </div>
</template>

<script>
import * as monaco from 'monaco-editor';

export default {
  name: 'WebEditor',
  data() {
    return {
      editor: null,
      selectedLanguage: 'javascript',
      languages: ['javascript', 'python', 'java', 'csharp']
    };
  },
  mounted() {
    this.initializeEditor(this.selectedLanguage);
  },
  beforeDestroy() {
    if (this.editor) {
      this.editor.dispose();
    }
  },
  methods: {
    initializeEditor(language) {
      this.editor = monaco.editor.create(document.getElementById('editor'), {
        value: this.getInitialCode(language),
        language: language
      });
    },
    getInitialCode(language) {
      switch (language) {
        case 'javascript':
          return '// JavaScript code here...';
        case 'python':
          return '# Python code here...';
        case 'java':
          return 'public class Main { public static void main(String[] args) { System.out.println("Hello, World!"); } }';
        case 'csharp':
          return 'using System; namespace HelloWorld { class Program { static void Main(string[] args) { Console.WriteLine("Hello, World!"); } } }';
        default:
          return '';
      }
    },
    changeLanguage() {
      const currentValue = this.editor.getValue();
      monaco.editor.setModelLanguage(this.editor.getModel(), this.selectedLanguage);
      this.editor.setValue(this.getInitialCode(this.selectedLanguage));
      this.editor.setValue(currentValue);
    }
  }
};
</script>

<style>
#editor {
  border: 1px solid #ccc;
}
</style>
