module.exports = function (config) {
    var root = 'run/compiled/karma/test'; // same as :output-dir
    var junitOutputDir = process.env.CIRCLE_TEST_REPORTS || "run/compiled/karma/test/junit";

    config.set({
        frameworks: ['cljs-test'],
        browsers: ['Chrome'],
        files: [
            root + '/../test.js', // same as :output-to
            {pattern: root + '/../test.js.map', included: false},
            {pattern: root + '/**/*.+(cljs|cljc|clj|js|js.map)', included: false}
        ],

        client: {
            args: ['com.smxemail.re_frame_document_fx.test_runner.run_karma']
        },

        // the default configuration
        junitReporter: {
            outputDir: junitOutputDir + '/karma', // results will be saved as $outputDir/$browserName.xml
            outputFile: undefined, // if included, results will be saved as $outputDir/$browserName/$outputFile
            suite: '' // suite will become the package name attribute in xml testsuite element
        }
    })
};
