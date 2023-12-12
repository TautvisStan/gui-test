using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace UI
{
    public partial class Analyzer : Form
    {
        public enum Rules
        {
            TooSmallControlCheck,
            TooLargeControlCheck,
            HiddenControlCheck,
            InsufficientSpaceCheck,
            InvisibleControlCheck,
            NoMarginsControlCheck,
            PoorChoiceOfColorsCheck,
            LowContrastCheck,
            EmptyViewCheck,
            NonCenteredCheck,

            UnalignedControlsCheck,
            ClippedControlCheck,
            ObscuredControlCheck,
            WrongLanguageCheck,
            ObscuredTextCheck,
            GrammarCheck,
            WrongEncodingCheck,
            ClippedTextCheck,
            UnlocalizedIconsCheck,
            MissingTranslationCheck,
            MixedLanguagesStateCheck,
            MixedLanguagesAppCheck,
            OffensiveMessagesCheck,
            UnreadableTextCheck,
            TooHardToUnderstandCheck,
            MissingTextCheck
        }
        public static List<string> SelectedRules = new List<string>();
        public static string ScreenshotDirectory = "";

        public Analyzer()
        {
            InitializeComponent();
        }

        private void Analyzer_Load(object sender, EventArgs e)
        {

        }

        private void checkBox11_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox12_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox13_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox14_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox15_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox16_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox17_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            if(TooSmallControlCheck.Checked)
            {
                if (!SelectedRules.Contains(Rules.TooSmallControlCheck.ToString()))
                    SelectedRules.Add(Rules.TooSmallControlCheck.ToString());
            }
            else
            {
                if (SelectedRules.Contains(Rules.TooSmallControlCheck.ToString()))
                    SelectedRules.Remove(Rules.TooSmallControlCheck.ToString());
            }
        }

        private void checkBox2_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox3_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox4_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox5_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox6_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox7_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox8_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox9_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void checkBox10_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            using (var fbd = new FolderBrowserDialog())
            {
                DialogResult result = fbd.ShowDialog();

                if (result == DialogResult.OK && !string.IsNullOrWhiteSpace(fbd.SelectedPath))
                {
                    string[] files = Directory.GetFiles(fbd.SelectedPath);
                    ScreenshotDirectory = fbd.SelectedPath;
                    System.Windows.Forms.MessageBox.Show("Files found: " + files.Length.ToString(), "Message");
                    label1.Text = "Current Screenshot Folder: " + ScreenshotDirectory;
                }
                else
                {
                    ScreenshotDirectory = "";
                    label1.Text = "Current Screenshot Folder: " + ScreenshotDirectory;
                }
            }
        }
    }
}
